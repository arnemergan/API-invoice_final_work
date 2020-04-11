package com.api.invoice.controllers;
import com.api.invoice.models.Invoice;
import com.api.invoice.models.UpdateInvoice;
import com.api.invoice.services.InvoiceServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping(path = "invoices")
public class InvoiceController {

    @Autowired
    private InvoiceServiceClass invoiceServiceClass;

    @GetMapping(path = {"/",""})
    @CrossOrigin
    public Page<Invoice> GetInvioces(Pageable pageable){
        return invoiceServiceClass.getInvoices(pageable);
    }
    @GetMapping(path = "/search/{number}")
    @CrossOrigin
    public Invoice GetInvoicesSearch(@PathVariable String number){return invoiceServiceClass.getInvoiceBySearchNumber(number);}
    @GetMapping(path = "/get/{id}")
    @CrossOrigin
    public Invoice GetInvoice(@PathVariable String id){
        return invoiceServiceClass.getInvoice(id);
    }
    @PostMapping(path = "/upload")
    @CrossOrigin
    public Invoice CreateInvoice(@ModelAttribute("image") MultipartFile  image, @ModelAttribute("lang") String language) throws IOException {
        return invoiceServiceClass.uploadInvoice(image,language);
    }
    @PutMapping(path = "/update/{id}")
    @CrossOrigin
    public Invoice UpdateInvoice(@PathVariable String id,@RequestBody UpdateInvoice invoice){
        return invoiceServiceClass.updateInvoice(id,invoice);
    }
    @DeleteMapping(path = "/delete/{id}")
    @CrossOrigin
    public void DeleteInvoice(@PathVariable String id){
        invoiceServiceClass.deleteInvoice(id);
    }
}
