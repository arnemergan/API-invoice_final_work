package com.api.invoice.dto.external;
import org.bson.types.Binary;
import java.util.List;

public class InvoiceDTO {
    private StringValue number;
    private String status;
    private StringValue dueDate;
    private StringValue invoiceDate;
    private FloatValue subtotal;
    private StringValue VAT;
    private FloatValue total;
    private StringValue currency;
    private Binary image;
    private VendorDTO vendor;
    private List<LineDTO> lines;

    public InvoiceDTO(){
    }

    public InvoiceDTO(StringValue number, StringValue dueDate, StringValue invoiceDate, FloatValue subtotal, StringValue VAT, FloatValue total, StringValue currency, Binary image, VendorDTO vendor, List<LineDTO> lines) {
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
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StringValue getDueDate() {
        return dueDate;
    }

    public void setDueDate(StringValue dueDate) {
        this.dueDate = dueDate;
    }

    public StringValue getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(StringValue invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public FloatValue getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(FloatValue subtotal) {
        this.subtotal = subtotal;
    }

    public StringValue getVAT() {
        return VAT;
    }

    public void setVAT(StringValue VAT) {
        this.VAT = VAT;
    }

    public FloatValue getTotal() {
        return total;
    }

    public void setTotal(FloatValue total) {
        this.total = total;
    }

    public StringValue getCurrency() {
        return currency;
    }

    public void setCurrency(StringValue currency) {
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

    public StringValue getNumber() {
        return number;
    }

    public void setNumber(StringValue number) {
        this.number = number;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }
}
