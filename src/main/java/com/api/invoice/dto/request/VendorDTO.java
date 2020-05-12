package com.api.invoice.dto.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

public class VendorDTO {
    @NotEmpty(message = "vendor.name cannot be empty")
    private String name;
    @NotEmpty(message = "vendor.address cannot be empty")
    private String address;
    @NotEmpty(message = "vendor.phone cannot be empty")
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "vendor.phone must be a valid phone number")
    private String phone;
    @NotEmpty(message = "vendor.email cannot be empty")
    @Email(message = "vendor.email must be a valid email")
    private String email;
    @NotEmpty(message = "vendor.vatNumber cannot be empty")
    @Pattern(regexp = "[A-Za-z]{2}[0-9|\\s]{8,15}$",message = "vendor.vatNumber must be a valid VATNumber")
    private String vatNumber;

    public VendorDTO(){};

    public VendorDTO(String name, String address, String phone, String email, String vatNumber) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.vatNumber = vatNumber;
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
