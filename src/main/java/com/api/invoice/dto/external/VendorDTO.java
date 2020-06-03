package com.api.invoice.dto.external;

public class VendorDTO {
    private StringValue name;
    private StringValue address;
    private StringValue phone;
    private StringValue email;
    private StringValue vatNumber;

    public VendorDTO(){};

    public VendorDTO(StringValue name, StringValue address, StringValue phone, StringValue email, StringValue vatNumber) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.vatNumber = vatNumber;
    }

    public StringValue getName() {
        return name;
    }

    public void setName(StringValue name) {
        this.name = name;
    }

    public StringValue getAddress() {
        return address;
    }

    public void setAddress(StringValue address) {
        this.address = address;
    }

    public StringValue getPhone() {
        return phone;
    }

    public void setPhone(StringValue phoneNumber) {
        this.phone = phoneNumber;
    }

    public StringValue getEmail() {
        return email;
    }

    public void setEmail(StringValue email) {
        this.email = email;
    }

    public StringValue getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(StringValue VATNumber) {
        this.vatNumber = VATNumber;
    }
}
