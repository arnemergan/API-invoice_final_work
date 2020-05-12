package com.api.invoice.models;

import org.bson.types.Binary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Document
public class Invoice extends BaseSaasEntity {
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    private String number;
    private String status;
    private List<String> errors;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$", message = "field must be a valid date")
    private String dueDate;
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$", message = "field must be a valid date")
    private String invoiceDate;
    @PositiveOrZero(message = "field must be positive")
    private Double subtotal;
    @Min(value = 0,message = "field must be between 0 and 21")
    @Max(value = 21,message = "field must be between 0 and 21")
    private Double VAT;
    @PositiveOrZero(message = "field must be positive")
    private Double total;
    private String currency;
    private Binary image;
    @ManyToOne
    @JoinColumn
    private User user;
    @ManyToOne
    @JoinColumn
    @Valid
    private Vendor vendor;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<@Valid Line> lines;

    public Invoice(){
        this.createdDate = new Date();
    }

    public Invoice(String number, List<String> errors, String dueDate, String invoiceDate, Double subtotal, Double VAT, Double total, String currency, Binary image, Vendor vendor, List<Line> lines) {
        this.errors = errors;
        this.image = image;
        this.number = number;
        this.dueDate = dueDate;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.VAT = VAT;
        this.total = total;
        this.currency = currency;
        this.vendor = vendor;
        this.lines = lines;
        this.createdDate = new Date();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> products) {
        this.lines = products;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
