package org.sinedmv.Cats.Entities.Dto;

import org.sinedmv.Cats.Entities.Models.Cat;
import org.sinedmv.Cats.Entities.Models.Owner;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OwnerDto implements Serializable {
    @NotNull
    @Positive(message = "Id must be positive")
    private int id;
    @NotBlank(message = "Owner name must not be blank")
    private String name;

    @NotNull(message = "Owner birthday date must not be null")
    @Past(message = "Birth date has to be in the past")
    private Date birthdayDate;

    public OwnerDto() {
    }

    public OwnerDto(Owner owner) {
        if (owner == null) {
            throw new NullPointerException("Owner cannot be null in OwnerDto constuctor");
        }
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthdayDate = owner.getBirthdayDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
