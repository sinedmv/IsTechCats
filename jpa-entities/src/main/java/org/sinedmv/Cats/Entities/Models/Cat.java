package org.sinedmv.Cats.Entities.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.sinedmv.Cats.Entities.Enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private Date birthdayDate;

    @NotBlank(message = "Breed is required")
    @Column(name = "breed", nullable = false)
    private String breed;

    @NotNull(message = "Color is required")
    @Column(name = "color", nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CatFriends",
            joinColumns = {@JoinColumn(name = "first_cat_id")},
            inverseJoinColumns = {@JoinColumn(name = "second_cat_id")})
    private List<Cat> friends;

    @PreRemove
    private void preRemove() {
        for (Cat friend : friends) {
            friend.getFriends().remove(this);
        }
        friends.clear();
    }

    public Cat() {
        friends = new ArrayList<>();
    }
    public Cat(String name, Date birthdayDate, String breed, Color color) {
        if (name == null || birthdayDate == null || breed == null || color == null) {
            throw new NullPointerException("Arguments cannot be null is Cat-constructor");
        }
        this.name = name;
        this.birthdayDate = birthdayDate;
        this.breed = breed;
        this.color = color;
        friends = new ArrayList<>();
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public void addFriend(Cat cat) {
        if (this.friends == null) {
            this.friends = new ArrayList<>();
        }
        if (cat == null) {
            throw new NullPointerException("Cat cannot be null");
        }
        friends.add(cat);
    }
    public void removeFriend(Cat cat) {
        if (this.friends == null) {
            this.friends = new ArrayList<>();
        }
        if (cat == null) {
            throw new NullPointerException("Cat cannot be null");
        }
        friends.remove(cat);
    }
    public List<Cat> getFriends() {
        return friends;
    }
    public void setFriends(List<Cat> cats) {
        friends = cats;
    }
    public String toString() {
        return "Id: " + id + "\n Name: " + name + "\n Breed: " + breed +
                "\n Color: " + color;
    }
}
