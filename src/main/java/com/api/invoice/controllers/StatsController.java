package com.api.invoice.controllers;
import com.api.invoice.models.Stats;
import com.api.invoice.services.implementation.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "statistics")
public class StatsController {
    @Autowired
    private InvoiceServiceImpl invoiceServiceClass;

    @GetMapping(path = {"/",""})
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Stats GetStats(HttpServletRequest request){
        return invoiceServiceClass.getStats(request.getHeader("Authorization").split(" ")[1]);
    }
}
