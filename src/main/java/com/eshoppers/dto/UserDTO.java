package com.eshoppers.dto;

import com.eshoppers.annotation.PasswordEqual;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@PasswordEqual(
        first = "password",
        second = "passwordConfirmed",
        message = "Password & Confirm Password Doesn't Match"
)
public class UserDTO {
    @NotEmpty
    @Size(min = 4, max = 32)
    private String username;

    @NotEmpty
    @Email
    @Size(min = 4, max = 64)
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String passwordConfirmed;

    @NotEmpty
    @Size(min = 1, max = 32)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 32)
    private String lastName;

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
