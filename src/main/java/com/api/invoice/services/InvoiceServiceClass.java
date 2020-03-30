package com.api.invoice.services;
import com.api.invoice.exceptions.ImageException;
import com.api.invoice.exceptions.InvoiceNotFoundException;
import com.api.invoice.models.Invoice;
import com.api.invoice.models.Vendor;
import com.api.invoice.repositories.InvoicesRepo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class InvoiceServiceClass implements InvoiceService {

    @Autowired
    private InvoicesRepo invoiceRepository;

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
    public Invoice uploadInvoice(MultipartFile image, String lang) throws IOException {
        if(image == null){
            throw new ImageException("Image must be included");
        }
        //step1: send image and language to nlp api
        //step2: get data image set to invoice object
        Invoice invoice = new Invoice();
        invoice.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        invoice.setLines(new ArrayList<>());
        invoice.setVendor(new Vendor());
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(String id, Invoice invoice) {
        Invoice invoice1 = invoiceRepository.getById(id);
        if(invoice1 == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public void deleteInvoice(String id) {
        Invoice invoice = invoiceRepository.getById(id);
        if(invoice == null){
            throw new InvoiceNotFoundException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }
}
