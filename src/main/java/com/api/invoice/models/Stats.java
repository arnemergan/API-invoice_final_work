package com.api.invoice.models;

import java.util.List;

public class Stats {
    private Long totalInvoices;
    private Month currentMonth;
    private Month BeforeCurrentMonth1;
    private Month BeforeCurrentMonth2;


    public Long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public Month getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Month currentMonth) {
        this.currentMonth = currentMonth;
    }

    public Month getBeforeCurrentMonth1() {
        return BeforeCurrentMonth1;
    }

    public void setBeforeCurrentMonth1(Month beforeCurrentMonth1) {
        BeforeCurrentMonth1 = beforeCurrentMonth1;
    }

    public Month getBeforeCurrentMonth2() {
        return BeforeCurrentMonth2;
    }

    public void setBeforeCurrentMonth2(Month beforeCurrentMonth2) {
        BeforeCurrentMonth2 = beforeCurrentMonth2;
    }
}
