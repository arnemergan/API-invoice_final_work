package com.api.invoice.controllers;
import com.api.invoice.models.Stats;
import com.api.invoice.services.InvoiceServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "statistics")
public class StatsController {
    @Autowired
    private InvoiceServiceClass invoiceServiceClass;

    @GetMapping(path = {"/",""})
    @CrossOrigin
    public Stats GetStats(){
        return invoiceServiceClass.getStats();
    }
}
