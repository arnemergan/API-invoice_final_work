package com.api.invoice.controllers;
import com.api.invoice.models.Invoice;
import com.api.invoice.dto.request.InvoiceDTO;
import com.api.invoice.services.implementation.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "invoices")
public class InvoiceController {

    @Autowired
    private InvoiceServiceImpl invoiceServiceClass;

    @GetMapping(path = {"/",""})
    public Page<Invoice> GetInvioces(Pageable pageable,HttpServletRequest request){
        return invoiceServiceClass.getInvoices(pageable,request.getHeader("Authorization").split(" ")[1]);
    }
    @GetMapping(path = "/username")
    public Page<Invoice> GetInviocesOnUsername(Pageable pageable,@RequestParam String username,HttpServletRequest request){
        return invoiceServiceClass.getInvoicesOnUsername(pageable,request.getHeader("Authorization").split(" ")[1],username);
    }
    @GetMapping(path = "/category")
    public Page<Invoice> GetInviocesOnCategory(Pageable pageable,@RequestParam String name ,HttpServletRequest request){
        return invoiceServiceClass.getInvoicesOnCategory(pageable,request.getHeader("Authorization").split(" ")[1],name);
    }
    @GetMapping(path = "/search/{number}")
    public Invoice GetInvoicesSearch(@PathVariable String number,HttpServletRequest request){
        return invoiceServiceClass.getInvoiceBySearchNumber(number,request.getHeader("Authorization").split(" ")[1]);
    }
    @GetMapping(path = "/image/{filename}")
    public ResponseEntity<Resource> GetImage(@PathVariable String filename, HttpServletRequest request){
        Resource file = invoiceServiceClass.getImage(request.getHeader("Authorization").split(" ")[1],filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping(path = "/get/{id}")
    public Invoice GetInvoice(@PathVariable String id,HttpServletRequest request){
        return invoiceServiceClass.getInvoice(id,request.getHeader("Authorization").split(" ")[1]);
    }
    @PostMapping(path = "/upload")
    public Invoice CreateInvoice(@ModelAttribute("image") MultipartFile  image, @ModelAttribute("lang") String language, HttpServletRequest request) throws IOException {
        return invoiceServiceClass.uploadInvoice(image,language,request.getHeader("Authorization").split(" ")[1]);
    }
    @PutMapping(path = "/update/{id}")
    @CrossOrigin
    public Invoice UpdateInvoice(@PathVariable String id, @RequestBody @Valid InvoiceDTO invoice, HttpServletRequest request){
        return invoiceServiceClass.updateInvoice(id,invoice,request.getHeader("Authorization").split(" ")[1]);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void DeleteInvoice(@PathVariable String id,HttpServletRequest request){
        invoiceServiceClass.deleteInvoice(id,request.getHeader("Authorization").split(" ")[1]);
    }
}
