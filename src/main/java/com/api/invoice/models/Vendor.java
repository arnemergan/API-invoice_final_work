package com.api.invoice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.UUID;

@Document
public class Vendor {
    @Id
    private String id;
    private String name;
    private String address;
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "phone must be a valid phone number")
    private String phone;
    @Email(message = "email must be a valid email")
    private String email;
    @Pattern(regexp = "[A-Za-z]{2}[0-9|\\s]{8,15}$",message = "vatNumber must be a valid VATNumber")
    private String vatNumber;
    @Transient
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    public Vendor(){};

    public Vendor(String name, String address, String phone, String email, String vatNumber) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.vatNumber = vatNumber;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String VATNumber) {
        this.vatNumber = VATNumber;
    }
}
