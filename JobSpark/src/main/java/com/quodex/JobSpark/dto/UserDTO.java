package com.quodex.JobSpark.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quodex.JobSpark.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) for User entity.
 * This class is used to transfer user data between different layers of the application.
 */


public class UserDTO {
    private Long id;
    @NotBlank(message = "{user.name.absent}")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{user.email.invalid}")
    @NotBlank(message = "{user.email.absent}")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{user.password.invalid}")
    @NotBlank(message = "{user.password.absent}")
    private String password;
    private AccountType accountType;

    private Long profileId;

    /**
     * Converts this DTO to a User entity.
     *
     * @return A new User entity populated with the DTO's data.
     */

    public User toEntity() {
        return new User(this.id, this.name, this.email, this.password, this.accountType, this.profileId);
    }

    // Default constructor
    public UserDTO() {
    }
    public UserDTO(Long id, String name, String email, String password, AccountType accountType, Long profileId) {
        this.id = id;

        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.profileId = profileId;
    }

    // Getter and setter methods for accessing and modifying private fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getProfileId()
    {
        return profileId;
    }

    public void setProfileId(Long profileId) {this.profileId = profileId;}
}
