package org.sinedmv.Cats.ApiMicroservice.Models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignInDto {
    @Size(min = 5, max = 50, message = "Size of username must be between 5 and 50 symbols")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Size(min = 4, max = 255, message = "Size of password must be between 4 and 255 symbols")
    private String password;

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
}
