package com.api.invoice.models;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Document
public class Line {
    @Id
    private String id;
    private String description;
    private Double unitPrice;
    private Integer quantity;
    private Double amount;
    @ManyToOne
    @JoinColumn
    private Invoice invoice;

    public Line(){
        id = UUID.randomUUID().toString();
    }

    public Line(String description, Double amount, Integer quantity, Double unitPrice) {
        id = UUID.randomUUID().toString();
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.amount = amount;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
