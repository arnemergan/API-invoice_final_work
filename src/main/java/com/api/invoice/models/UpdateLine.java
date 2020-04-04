package com.api.invoice.models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

public class UpdateLine {
    @NotEmpty(message = "line.description cannot be empty")
    private String description;
    @NotNull(message = "line.unitprice cannot be empty")
    @PositiveOrZero(message = "line.unitprice must be positive")
    private Double unitPrice;
    @NotNull(message = "line.quantity cannot be empty")
    @PositiveOrZero(message = "line.quantity must be positive")
    private Integer quantity;
    @Min(0)
    @Max(21)
    private Integer vat;
    @PositiveOrZero(message = "line.amount must be positive")
    private Double amount;

    public UpdateLine(){
    }

    public UpdateLine(String description, Double amount, Integer quantity, Integer vat) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.vat = vat;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
