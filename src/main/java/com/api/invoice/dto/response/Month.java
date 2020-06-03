package com.api.invoice.dto.response;

public class Month {
    private Integer total;
    private Double totalPrice;

    public Month(Integer total, Double totalPrice) {
        this.total = total;
        this.totalPrice = totalPrice;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
