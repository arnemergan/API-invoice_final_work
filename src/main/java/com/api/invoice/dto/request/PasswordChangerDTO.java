package com.api.invoice.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PasswordChangerDTO {
    @NotEmpty(message = "old password is required")
    @NotNull(message = "old password is required")
    private String oldPassword;

    @Length(min=8, max=100,message = "new password must be between 8 and 100")
    @NotEmpty(message = "new password is required")
    @NotNull(message = "new password is required")
    private String newPassword;

    public PasswordChangerDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
