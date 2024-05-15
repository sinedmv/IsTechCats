package org.sinedmv.Cats.Entities.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private Date birthdayDate;

    public Owner(String name, Date birthdayDate) {
        if (name == null || birthdayDate == null) {
            throw new NullPointerException("Arguments cannot be null in Owner-constructor");
        }
        this.name = name;
        this.birthdayDate = birthdayDate;
    }
    public Owner() {
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

    public String ToString() {
        return "Id: " + id + "\n Name: " + name;
    }
}