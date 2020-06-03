package com.api.invoice.dto.external;

public class LineDTO {
    private StringValue description;
    private FloatValue unitPrice;
    private FloatValue quantity;
    private FloatValue amount;

    public LineDTO(){
    }

    public LineDTO(StringValue description,FloatValue unitPrice, FloatValue amount, FloatValue quantity) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.amount = amount;
    }

    public StringValue getDescription() {
        return description;
    }

    public void setDescription(StringValue description) {
        this.description = description;
    }

    public FloatValue getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(FloatValue unitPrice) {
        this.unitPrice = unitPrice;
    }

    public FloatValue getQuantity() {
        return quantity;
    }

    public void setQuantity(FloatValue quantity) {
        this.quantity = quantity;
    }

    public FloatValue getAmount() {
        return amount;
    }

    public void setAmount(FloatValue amount) {
        this.amount = amount;
    }
}
