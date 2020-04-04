package com.api.invoice.models;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class UpdateInvoice {
    @NotEmpty(message = "number cannot be empty")
    private String number;
    @NotEmpty(message = "duedate cannot be empty")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$", message = "duedate must be a valid date")
    private String dueDate;
    @NotEmpty(message = "invoicedate cannot be empty")
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$", message = "invoicedate must be a valid date")
    private String invoiceDate;
    @NotNull(message = "subtotal cannot be empty")
    @PositiveOrZero(message = "subtotal must be positive")
    private Double subtotal;
    @NotNull(message = "vat cannot be empty")
    @Min(value = 0,message = "vat must be between 0 and 21")
    @Max(value = 21,message = "vat must be between 0 and 21")
    private Double VAT;
    @NotNull(message = "discount cannot be empty")
    @PositiveOrZero(message = "field must be positive")
    private Double discount;
    @NotNull(message = "total cannot be empty")
    @PositiveOrZero(message = "field must be positive")
    private Double total;
    @NotEmpty(message = "currency cannot be empty")
    private String currency;
    @NotNull(message = "vendor cannot be empty")
    @ManyToOne
    @JoinColumn
    @Valid
    private UpdateVendor vendor;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<@Valid UpdateLine> lines;

    public UpdateInvoice(Double discount, String number, String dueDate, String invoiceDate, Double subtotal, Double VAT, Double total, String currency, UpdateVendor vendor, List<UpdateLine> lines) {
        this.number = number;
        this.dueDate = dueDate;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.VAT = VAT;
        this.discount = discount;
        this.total = total;
        this.currency = currency;
        this.vendor = vendor;
        this.lines = lines;
    }

    public UpdateInvoice(){

    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UpdateVendor getVendor() {
        return vendor;
    }

    public void setVendor(UpdateVendor vendor) {
        this.vendor = vendor;
    }

    public List<UpdateLine> getLines() {
        return lines;
    }

    public void setLines(List<UpdateLine> products) {
        this.lines = products;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
