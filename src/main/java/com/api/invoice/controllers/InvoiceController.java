package com.api.invoice.controllers;
import com.api.invoice.models.Invoice;
import com.api.invoice.services.InvoiceServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "invoices")
public class InvoiceController {

    @Autowired
    private InvoiceServiceClass invoiceServiceClass;

    @GetMapping(path = {"/",""})
    public Page<Invoice> GetInvioces(Pageable pageable){
        return invoiceServiceClass.getInvoices(pageable);
    }
    @GetMapping(path = "/get/{id}")
    public Invoice GetInvoice(@PathVariable String id){
        return invoiceServiceClass.getInvoice(id);
    }
    @PostMapping(path = "/upload")
    public Invoice CreateInvoice(@ModelAttribute("image") MultipartFile  image, @ModelAttribute("lang") String language) throws IOException {
        return invoiceServiceClass.uploadInvoice(image,language);
    }

    @PutMapping(path = "/update/{id}")
    public Invoice UpdateInvoice(@PathVariable String id,@Valid @RequestBody Invoice invoice){
        return invoiceServiceClass.updateInvoice(id,invoice);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void DeleteInvoice(@PathVariable String id){
        invoiceServiceClass.deleteInvoice(id);
    }
}
