package com.api.invoice.services.implementation;
import com.api.invoice.dto.external.FloatValue;
import com.api.invoice.dto.external.LineDTO;
import com.api.invoice.dto.external.StringValue;
import com.api.invoice.dto.request.InvoiceDTO;
import com.api.invoice.dto.response.Month;
import com.api.invoice.dto.response.Stats;
import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.exceptions.ImageException;
import com.api.invoice.exceptions.InvoiceNotFoundException;
import com.api.invoice.external.OcrandNlpServiceImpl;
import com.api.invoice.models.*;
import com.api.invoice.repositories.InvoicesRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoicesRepo invoiceRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private OcrandNlpServiceImpl ocrandNlpService;

    @Override
    public Invoice getInvoice(String id, String token) {
        Invoice invoice = invoiceRepository.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        return invoice;
    }

    @Override
    public Invoice getInvoiceBySearchNumber(String number, String token){
        Invoice invoice = invoiceRepository.getInvoiceByNumberAndTenantId(number,tokenUtils.getTenantFromToken(token));
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        return invoice;
    }

    @Override
    public Resource getImage(String token, String filename) {
        Invoice invoice = invoiceRepository.getInvoiceByFilenameAndTenantId(filename,tokenUtils.getTenantFromToken(token));
        if(invoice == null){
            throw new InvoiceNotFoundException("Image not found");
        }
        return fileStorageService.load(filename);
    }

    @Override
    public Page<Invoice> getInvoicesOnCategory(Pageable pageable, String token, String name) {
        return invoiceRepository.findAllByTenantIdAndCategory(tokenUtils.getTenantFromToken(token),categoryService.getByName(token,name),pageable);
    }

    @Override
    public Page<Invoice> getInvoicesOnUsername(Pageable pageable, String token, String username) {
        return invoiceRepository.findAllByTenantIdAndUsername(tokenUtils.getTenantFromToken(token),username,pageable);
    }

    @Override
    public Invoice setDone(String token, String id, boolean done) {
        Invoice invoice = invoiceRepository.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        invoice.setDone(done);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Page<Invoice> getInvoices(Pageable pageable, String token) {
        return invoiceRepository.findAllByTenantId(tokenUtils.getTenantFromToken(token),pageable);
    }

    @Override
    public Invoice uploadInvoice(MultipartFile image, String lang, String token) {
        User user = userRepo.findUserByUsername(tokenUtils.getUsernameFromToken(token));
        if(user == null){
            throw new ApiRequestException("User must be valid");
        }
        if(image == null){
            throw new ImageException("Image must be included");
        }
        try {
            Invoice invoice = mapInvoiceDTO(ocrandNlpService.getInvoice(image,lang));
            if(invoice == null){
                throw new ImageException("Something went wrong with the image");
            }
            if(invoice.getVendor() == null){
                invoice.setVendor(new Vendor());
            }
            if(invoice.getLines() == null){
                invoice.setLines(new ArrayList<>());
            }
            invoice.setDone(false);
            invoice.setCategory(categoryService.getDefault());
            invoice.setUsername(user.getUsername());
            invoice.setTenantId(user.getTenantId());
            fileStorageService.save(image);
            invoice.setFilename(StringUtils.cleanPath(image.getOriginalFilename()));
            return invoiceRepository.save(invoice);
        }catch (Exception e){
            throw new ImageException("Something went wrong with the image");
        }
    }

    @Override
    public Invoice updateInvoice(String id, InvoiceDTO invoice, String token) {
        Invoice invoice1 = invoiceRepository.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(invoice1 == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(invoice,invoice1);
        invoice1.setCategory(categoryService.getByName(token,invoice.getCategoryName()));
        invoice1.setLastModifiedDate(new Date());
        return invoiceRepository.save(invoice1);
    }

    @Override
    public void deleteInvoice(String id, String token) {
        Invoice invoice = invoiceRepository.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

    private Invoice mapInvoiceDTO(com.api.invoice.dto.external.InvoiceDTO invoiceDTO){
        if(invoiceDTO == null){
            return null;
        }
        Invoice invoice = new Invoice();
        invoice.setCurrency(getValue(invoiceDTO.getCurrency()));
        invoice.setNumber(getValue(invoiceDTO.getNumber()));
        invoice.setTotal(getValue(invoiceDTO.getTotal()));
        invoice.setSubtotal(getValue(invoiceDTO.getSubtotal()));
        invoice.setDueDate(getValue(invoiceDTO.getDueDate()));
        invoice.setInvoiceDate(getValue(invoiceDTO.getInvoiceDate()));
        invoice.setVendor(new Vendor(getValue(invoiceDTO.getVendor().getName()), getValue(invoiceDTO.getVendor().getAddress()), getValue(invoiceDTO.getVendor().getPhone()), getValue(invoiceDTO.getVendor().getEmail()), getValue(invoiceDTO.getVendor().getVatNumber())));
        List<Line> lines = new ArrayList<Line>();
        for (LineDTO line: invoiceDTO.getLines()) {
            lines.add(new Line(getValue(line.getDescription()), getValue(line.getAmount()),getValue(line.getQuantity()).intValue(),getValue(line.getUnitPrice())));
        }
        invoice.setLines(lines);
        return invoice;
    }

    private String getValue(StringValue value){
       return value.getValue();
    }

    private Double getValue(FloatValue value){
        return value.getValue();
    }
}
