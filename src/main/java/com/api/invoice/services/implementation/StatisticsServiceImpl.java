package com.api.invoice.services.implementation;

import com.api.invoice.dto.response.Month;
import com.api.invoice.dto.response.Stats;
import com.api.invoice.exceptions.ImageException;
import com.api.invoice.models.Category;
import com.api.invoice.models.Invoice;
import com.api.invoice.repositories.InvoicesRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private InvoicesRepo invoicesRepo;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Stats getStats(String token) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        Stats stats = new Stats();
        stats.setBeforeCurrentMonth1(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantId(getDate(-2),getDate(-1),tenantId),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantId(getDate(-2),getDate(-1),tenantId))
        ));
        stats.setBeforeCurrentMonth2(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantId(getDate(-3),getDate(-2),tenantId),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantId(getDate(-3),getDate(-2),tenantId))
        ));
        stats.setCurrentMonth(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantId(getDate(-1),getDate(0),tenantId),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantId(getDate(-1),getDate(0),tenantId))
        ));
        stats.setTotalPrice(invoicesRepo.sumTotalPriceAndTenantId(tenantId));
        stats.setCount(invoicesRepo.countAllByTenantId(tenantId));
        return stats;
    }

    @Override
    public Stats getStatsOnCategory(String token, String name) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        Category category = categoryService.getByName(token,name);
        Stats stats = new Stats();
        stats.setBeforeCurrentMonth1(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndCategory(getDate(-2),getDate(-1),tenantId,category),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndCategory(getDate(-2),getDate(-1),tenantId,category))
        ));
        stats.setBeforeCurrentMonth2(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndCategory(getDate(-3),getDate(-2),tenantId,category),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndCategory(getDate(-3),getDate(-2),tenantId,category))
        ));
        stats.setCurrentMonth(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndCategory(getDate(-1),getDate(0),tenantId,category),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndCategory(getDate(-1),getDate(0),tenantId,category))
        ));
        stats.setTotalPrice(getTotalPrice(invoicesRepo.getAllByTenantIdAndCategory(tenantId,category)));
        stats.setCount(invoicesRepo.countAllByTenantIdAndCategory(tenantId,category));
        return stats;
    }

    @Override
    public Stats getStatsOnUsername(String token, String username) {
        String tenantId = tokenUtils.getTenantFromToken(token);
        Stats stats = new Stats();
        stats.setBeforeCurrentMonth1(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndUsername(getDate(-2),getDate(-1),tenantId,username),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndUsername(getDate(-2),getDate(-1),tenantId,username))
        ));
        stats.setBeforeCurrentMonth2(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndUsername(getDate(-3),getDate(-2),tenantId,username),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndUsername(getDate(-3),getDate(-2),tenantId,username))
        ));
        stats.setCurrentMonth(setMonth(
                invoicesRepo.countByCreatedDateBetweenAndTenantIdAndUsername(getDate(-1),getDate(0),tenantId,username),
                getTotalPrice(invoicesRepo.getAllByCreatedDateBetweenAndTenantIdAndUsername(getDate(-1),getDate(0),tenantId,username))
        ));
        stats.setTotalPrice(getTotalPrice(invoicesRepo.getAllByTenantIdAndUsername(tenantId,username)));
        stats.setCount(invoicesRepo.countAllByTenantIdAndUsername(tenantId,username));
        return stats;
    }

    private Month setMonth(Integer count, Double totalPrice){
        return new Month(count,totalPrice);
    }

    private double getTotalPrice(List<Invoice> invoiceList){
        Double total = 0.0;
        for (Invoice invoice:invoiceList) {
            if(invoice.getTotal() != null){
                total += invoice.getTotal();
            }
        }
        return total;
    }

    private Date getDate(Integer number){
        Calendar month = new GregorianCalendar();
        month.add(Calendar.MONTH, number);
        return month.getTime();
    }
}
