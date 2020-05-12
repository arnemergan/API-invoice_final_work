package com.api.invoice.services.implementation;
import com.api.invoice.dto.request.InvoiceDTO;
import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.exceptions.ImageException;
import com.api.invoice.exceptions.InvoiceNotFoundException;
import com.api.invoice.exceptions.InvoiceUpdateException;
import com.api.invoice.models.*;
import com.api.invoice.repositories.InvoicesRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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

    private final String uri = "https://python-final-work.herokuapp.com/api/v1/ocr?language=";

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
            Invoice invoice;
            Binary bin = new Binary(BsonBinarySubType.BINARY, image.getBytes());
            invoice = invoiceRepository.getByImage(bin);
            if(invoice != null) {
                return invoice;
            }else{
                invoice = new Invoice();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("image", new FileSystemResource(convert(image)));
                HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
                ResponseEntity response = new RestTemplate().postForEntity(uri + lang, entity, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        invoice = objectMapper.readValue(response.getBody().toString(), Invoice.class);
                    }catch (JsonProcessingException ex){
                        throw new ImageException("Error during processing json");
                    }
                    if(invoice.getVendor() == null){
                        invoice.setVendor(new Vendor());
                    }
                    if(invoice.getLines() == null){
                        invoice.setLines(new ArrayList<>());
                    }
                }else if(response.getStatusCode() == HttpStatus.BAD_REQUEST || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                    throw new ImageException("Error during processing image");
                }else{
                    invoice.setLines(new ArrayList<>());
                    invoice.setVendor(new Vendor());
                }
                if(invoice.getErrors().size() > 0 || invoice.getErrors() == null){
                    invoice.setStatus("work");
                }else{
                    invoice.setStatus("done");
                }
                invoice.setUser(user);
                invoice.setTenantId(user.getTenantId());
                invoice.setImage(bin);
                return invoiceRepository.save(invoice);
            }
        }catch (IOException e){
            throw new ImageException("Something went wrong with the image");
        }
    }

    @Override
    public Invoice updateInvoice(String id, InvoiceDTO invoice, String token) {
        Invoice invoice1 = invoiceRepository.getByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(invoice1 == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        List<String> errors = validateInvoice(invoice);
        if(errors.size() > 0){
            throw new InvoiceUpdateException(errors.toString());
        }
        if(invoice1.getErrors().size() > 0){
            invoice1.setStatus("work");
        }else{
            invoice1.setStatus("done");
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(invoice,invoice1);
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

    @Override
    public Stats getStats(String token) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        Stats stats = new Stats();
        Month current = new Month();
        Month prev = new Month();
        Month prev2 = new Month();
        current.setTotalPrice(getTotalPrice(invoiceRepository.getAllByCreatedDateBetweenAndTenantId(getDate(-1),getDate(0),tenantId)));
        current.setTotal(invoiceRepository.countByCreatedDateBetweenAndTenantId(getDate(-1),getDate(0),tenantId));
        prev.setTotalPrice(getTotalPrice(invoiceRepository.getAllByCreatedDateBetweenAndTenantId(getDate(-2),getDate(-1),tenantId)));
        prev.setTotal(invoiceRepository.countByCreatedDateBetweenAndTenantId(getDate(-2),getDate(-1),tenantId));
        prev2.setTotalPrice(getTotalPrice(invoiceRepository.getAllByCreatedDateBetweenAndTenantId(getDate(-3),getDate(-2),tenantId)));
        prev2.setTotal(invoiceRepository.countByCreatedDateBetweenAndTenantId(getDate(-3),getDate(-2),tenantId));
        stats.setBeforeCurrentMonth1(prev);
        stats.setBeforeCurrentMonth2(prev2);
        stats.setCurrentMonth(current);
        stats.setTotalPrice(invoiceRepository.sumTotalPriceAndTenantId(tenantId));
        stats.setCount(invoiceRepository.countAllByTenantId(tenantId) - 1);
        return stats;
    }

    public double getTotalPrice(List<Invoice> invoiceList){
        Double total = 0.0;
        for (Invoice invoice:invoiceList) {
            total += invoice.getTotal();
        }
        return total;
    }

    public Date getDate(Integer number){
        Calendar month = new GregorianCalendar();
        month.add(Calendar.MONTH, number);
        return month.getTime();
    }

    public List<String> validateInvoice(InvoiceDTO invoice){
        List<String> errors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<InvoiceDTO>> violations = validator.validate(invoice);
        for (ConstraintViolation violation: violations) {
            errors.add(violation.getMessage());
        }
        return errors;
    }

    public static File convert(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
}
