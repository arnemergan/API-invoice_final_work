package com.api.invoice.models;

public class Stats {
    private Integer count;
    private Month currentMonth;
    private Month BeforeCurrentMonth1;
    private Month BeforeCurrentMonth2;
    private Double totalPrice;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
