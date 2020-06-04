package com.api.invoice.controllers;
import com.api.invoice.dto.response.Stats;
import com.api.invoice.services.StatisticsService;
import com.api.invoice.services.implementation.InvoiceServiceImpl;
import com.api.invoice.services.implementation.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "statistics")
public class StatsController {
    @Autowired
    private StatisticsServiceImpl statisticsService;

    @GetMapping(path = {"/",""})
    public Stats GetStats(HttpServletRequest request){
        return statisticsService.getStats(request.getHeader("Authorization").split(" ")[1]);
    }
    @GetMapping(path = "/category")
    public Stats GetStatsOnCategory(HttpServletRequest request,@RequestParam String name){
        return statisticsService.getStatsOnCategory(request.getHeader("Authorization").split(" ")[1], name);
    }

    @GetMapping(path = "/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Stats GetStatsOnUsername(HttpServletRequest request, @RequestParam String username){
        return statisticsService.getStatsOnUsername(request.getHeader("Authorization").split(" ")[1],username);
    }
}
