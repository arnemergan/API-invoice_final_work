package com.api.invoice.dto.request;
import com.api.invoice.models.Category;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class InvoiceDTO {
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
    private Double vatNumber;
    @NotNull(message = "total cannot be empty")
    @PositiveOrZero(message = "field must be positive")
    private Double total;
    @NotEmpty(message = "currency cannot be empty")
    private String currency;
    @NotNull(message = "vendor cannot be empty")
    @ManyToOne
    @JoinColumn
    @Valid
    private VendorDTO vendor;
    private String categoryName;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<@Valid LineDTO> lines;

    public InvoiceDTO(Double discount, String number, String dueDate, String invoiceDate, Double subtotal, Double VAT, Double total, String currency, VendorDTO vendor, List<LineDTO> lines) {
        this.number = number;
        this.dueDate = dueDate;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.vatNumber = VAT;
        this.total = total;
        this.currency = currency;
        this.vendor = vendor;
        this.lines = lines;
    }

    public InvoiceDTO(){

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

    public Double getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(Double vatNumber) {
        this.vatNumber = vatNumber;
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

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public List<LineDTO> getLines() {
        return lines;
    }

    public void setLines(List<LineDTO> products) {
        this.lines = products;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
