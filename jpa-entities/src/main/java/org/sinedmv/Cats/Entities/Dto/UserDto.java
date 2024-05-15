package org.sinedmv.Cats.Entities.Dto;

import org.sinedmv.Cats.Entities.Models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserDto {
    @Size(min = 5, max = 50, message = "Size of username must be between 5 and 50 symbols")
    @NotBlank(message = "Username must not be blank")
    private String username;

    @Size(min = 4, max = 255, message = "Size of password must be between 4 and 255 symbols")
    private String password;

    @NotBlank(message = "Owner name must not be blank")
    private String name;

    @NotNull(message = "Owner birthday date must not be null")
    @Past(message = "Birth date has to be in the past")
    private Date birthdayDate;

    @NotNull(message = "Owner must not be null")
    private OwnerDto owner;

    public UserDto(){

    }

    public UserDto(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null in UserDto constructor");
        }
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getOwner().getName();
        this.birthdayDate = user.getOwner().getBirthdayDate();
        this.owner = new OwnerDto(user.getOwner());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}
