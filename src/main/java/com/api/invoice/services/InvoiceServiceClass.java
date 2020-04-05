package com.api.invoice.services;
import com.api.invoice.exceptions.ImageException;
import com.api.invoice.exceptions.InvoiceNotFoundException;
import com.api.invoice.exceptions.InvoiceUpdateException;
import com.api.invoice.models.Invoice;
import com.api.invoice.models.UpdateInvoice;
import com.api.invoice.models.UpdateLine;
import com.api.invoice.models.Vendor;
import com.api.invoice.repositories.InvoicesRepo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceServiceClass implements InvoiceService {

    @Autowired
    private InvoicesRepo invoiceRepository;

    private final String uri = "https://python-final-work.herokuapp.com/api/v1/ocr?language=";

    @Override
    public Invoice getInvoice(String id) {
        Invoice invoice = invoiceRepository.getById(id);
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        return invoice;
    }

    @Override
    public Page<Invoice> getInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Invoice uploadInvoice(MultipartFile image, String lang) {
        if(image == null){
            throw new ImageException("Image must be included");
        }
        Invoice invoice;
        Binary bin;
        try {
            bin = new Binary(BsonBinarySubType.BINARY, image.getBytes());
            invoice = invoiceRepository.getByImage(bin);
        }catch (IOException e){
            throw new ImageException("Something went wrong with the image");
        }
        if(invoice != null) {
            return invoice;
        }
        invoice = new Invoice();
        invoice.setImage(bin);
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
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(String id, UpdateInvoice invoice) {
        Invoice invoice1 = invoiceRepository.getById(id);
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
        return invoiceRepository.save(invoice1);
    }

    @Override
    public void deleteInvoice(String id) {
        Invoice invoice = invoiceRepository.getById(id);
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

    public List<String> validateInvoice(UpdateInvoice invoice){
        List<String> errors = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UpdateInvoice>> violations = validator.validate(invoice);
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
