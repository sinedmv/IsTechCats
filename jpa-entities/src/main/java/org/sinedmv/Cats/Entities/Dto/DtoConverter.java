package org.sinedmv.Cats.Entities.Dto;

import org.sinedmv.Cats.Entities.Enums.Color;
import org.sinedmv.Cats.Entities.Models.Cat;
import org.sinedmv.Cats.Entities.Models.Owner;
import org.sinedmv.Cats.Entities.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    public Cat asEntity(CatDto catDto) {
        Cat resultCat = new Cat();
        resultCat.setId(catDto.getId());
        resultCat.setName(catDto.getName());
        resultCat.setBreed(catDto.getBreed());
        resultCat.setColor(Color.valueOf(catDto.getColor()));
        resultCat.setBirthdayDate(catDto.getBirthdayDate());
        resultCat.setOwner(asEntity(catDto.getOwner()));
        return resultCat;
    }
    public Owner asEntity(OwnerDto ownerDto) {
        Owner resulstOwner = new Owner();
        resulstOwner.setId(ownerDto.getId());
        resulstOwner.setName(ownerDto.getName());
        resulstOwner.setBirthdayDate(ownerDto.getBirthdayDate());
        return resulstOwner;
    }

    public User asEntity(UserDto userDto) {
        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setOwner(asEntity(userDto.getOwner()));
        return user;
    }
}
