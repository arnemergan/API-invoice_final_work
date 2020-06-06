package com.api.invoice.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserInfoChangerDTO {
    @Length(min=8, max=20,message = "username must be between 8 and 20")
    @NotEmpty(message = "username is required")
    private String username;
    @NotEmpty(message = "firstname is required")
    private String firstName;
    @NotEmpty(message = "lastname is required")
    private String lastName;
    @NotEmpty(message = "email is required")
    private String email;

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

}
