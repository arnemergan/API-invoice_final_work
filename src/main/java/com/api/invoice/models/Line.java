package com.api.invoice.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.util.UUID;

@Entity
public class Line {
    @Id
    private String id;
    private String description;
    @PositiveOrZero(message = "field must be positive")
    private Double unitPrice;
    @PositiveOrZero(message = "field must be positive")
    private Integer quantity;
    @Min(0)
    @Max(21)
    private Integer vat;
    @PositiveOrZero(message = "field must be positive")
    private Double totalPrice;
    @ManyToOne
    @JoinColumn
    private Invoice invoice;

    public Line(String description, Double unitPrice, Integer quantity, Integer vat) {
        id = UUID.randomUUID().toString();
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.vat = vat;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
