package com.api.invoice.models;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Document
public class Invoice extends BaseSaasEntity {
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
    private String number;
    private String dueDate;
    private String invoiceDate;
    private Double subtotal;
    private Double vatNumber;
    private Double total;
    private String currency;
    private String filename;
    private String username;
    private boolean done;
    @ManyToOne
    @JoinColumn
    private Category category;
    @ManyToOne
    @JoinColumn
    private Vendor vendor;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<@Valid Line> lines;

    public Invoice(){
        this.createdDate = new Date();
    }

    public Invoice(Category category ,String number, String dueDate, String invoiceDate, Double subtotal, Double vatNumber, Double total, String currency, String filename, Vendor vendor, List<Line> lines) {
        this.category = category;
        this.filename = filename;
        this.number = number;
        this.dueDate = dueDate;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.vatNumber = vatNumber;
        this.total = total;
        this.currency = currency;
        this.vendor = vendor;
        this.lines = lines;
        this.createdDate = new Date();
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
