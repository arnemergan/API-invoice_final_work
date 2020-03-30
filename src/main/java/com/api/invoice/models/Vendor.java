package com.api.invoice.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.UUID;

@Entity
public class Vendor {
    @Id
    private String id;
    private String name;
    private String address;
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$")
    private String phoneNumber;
    @Email(message = "field must be a valid email")
    private String email;
    @Pattern(regexp = "^(BE)?0[0-9]{9}$|^(FR)?[0-9A-Z]{2}[0-9]{9}$|^(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})$",message = "field must be a valid VATNumber")
    private String VATNumber;
    @Transient
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    public Vendor(){};

    public Vendor(String name, String address, String phoneNumber, String email, String VATNumber) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.VATNumber = VATNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVATNumber() {
        return VATNumber;
    }

    public void setVATNumber(String VATNumber) {
        this.VATNumber = VATNumber;
    }
}
