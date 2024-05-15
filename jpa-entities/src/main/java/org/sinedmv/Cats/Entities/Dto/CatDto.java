package org.sinedmv.Cats.Entities.Dto;

import org.sinedmv.Cats.Entities.Models.Cat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatDto implements Serializable {
    @PositiveOrZero(message = "Id must be positive or zero")
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birthday date is required")
    @Past(message = "Birth date has to be in the past")
    private Date birthdayDate;

    @NotBlank(message = "Breed is required")
    private String breed;

    @NotBlank(message = "Color is required")
    private String color;

    private OwnerDto owner;
    private List<@Positive Integer> friendsId;

    public CatDto() {
        this.friendsId = new ArrayList<>();
    }

    public CatDto(Cat cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.birthdayDate = cat.getBirthdayDate();
        this.breed = cat.getBreed();
        this.color = cat.getColor().toString();
        if (cat.getOwner() != null) {
            this.owner = new OwnerDto(cat.getOwner());
        }
        friendsId = new ArrayList<>();
        for (Cat friend : cat.getFriends()) {
            friendsId.add(friend.getId());
        }
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
