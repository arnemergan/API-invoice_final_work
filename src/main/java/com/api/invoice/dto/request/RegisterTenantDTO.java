package com.api.invoice.dto.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class RegisterTenantDTO {
    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "country is required")
    private String country;

    @NotNull(message = "maxEmployees is required")
    private int maxEmployees;

    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "firstName is required")
    private String firstName;
    @NotNull(message = "lastName is required")
    private String lastName;
    @NotNull(message = "email is required")
    @Email(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public void setMaxEmployees(int maxEmployees) {
        this.maxEmployees = maxEmployees;
    }
}
