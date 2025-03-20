package com.quodex.JobSpark.dto;

import com.quodex.JobSpark.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) for User entity.
 * This class is used to transfer user data between different layers of the application.
 */


public class LoginDTO {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.absent}")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{user.password.invalid}")
    @NotBlank(message = "{user.password.absent}")
    private String password;
    private AccountType accountType;

    /**
     * Converts this DTO to a User entity.
     *
     * @return A new User entity populated with the DTO's data.
     */

    // No args Constructor
    public LoginDTO() {
    }

    // All args Constructor
    public LoginDTO(String email, String password, AccountType accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    // Getter and setter methods for accessing and modifying private fields


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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
