package org.sinedmv.Cats.CatMicroservice.Services;

import org.sinedmv.Cats.CatMicroservice.Dao.CatRepository;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.DtoConverter;
import org.sinedmv.Cats.Entities.Enums.Color;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Exceptions.InvalidStringAsEnumException;
import org.sinedmv.Cats.Entities.Models.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatServiceImpl implements CatService {
    private CatRepository catRepository;
    private DtoConverter dtoConverter;
    @Autowired
    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
        this.dtoConverter = new DtoConverter();
    }

    @Override
    @Transactional
    public void add(CatDto catDto) throws DaoException {
        if (catDto == null) {
            throw new NullPointerException("Cat cannot be null");
        }
        if (catRepository.existsById(catDto.getId())) {
            throw new DaoException("Cat with this ID is already exists:" + catDto.getId());
        }
        catRepository.save(dtoConverter.asEntity(catDto));
    }

    @Override
    @Transactional
    public void update(CatDto catDto) throws DaoException {
        if (catDto == null) {
            throw new NullPointerException("Cat cannot be null");
        }
        if (!catRepository.existsById(catDto.getId())) {
            throw new DaoException("Cat with this ID doesn't exist:" + catDto.getId());
        }
        catRepository.save(dtoConverter.asEntity(catDto));
    }

    @Override
    @Transactional
    public void delete(Integer id) throws DaoException {
        if (!catRepository.existsById(id)) {
            throw new DaoException("Cat with this ID doesn't exist:" + id);
        }
        catRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<CatDto> getByName(String name) throws DaoException {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        List<CatDto> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByName(name)){
            cats.add(new CatDto(cat));
        }
        return cats;
    }

    @Override
    @Transactional
    public List<CatDto> getByBreed(String breed) {
        if (breed == null) {
            throw new NullPointerException("Breed cannot be null");
        }
        List<CatDto> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByBreed(breed)){
            cats.add(new CatDto(cat));
        }
        return cats;
    }

    @Override
    @Transactional
    public List<CatDto> getByColor(String color) throws InvalidStringAsEnumException {
        if (color == null) {
            throw new NullPointerException("Color cannot be null");
        }
        Color colorAsEnum;
        try {
            colorAsEnum = Color.valueOf(color);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidStringAsEnumException("Invalid color name:" + color);
        }
        List<CatDto> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(colorAsEnum)){
            cats.add(new CatDto(cat));
        }
        return cats;
    }

    @Override
    @Transactional
    public CatDto getById(Integer id) throws DaoException {
        var cat = catRepository.findCatById(id);
        if (cat.isEmpty()) {
            throw new DaoException("Cat with this ID doesn't exist:" + id);
        }
        return new CatDto(cat.get());
    }

    @Override
    @Transactional
    public List<CatDto> getAll() {
        List<CatDto> dtos = new ArrayList<>();
        for (Cat cat : catRepository.findAll()) {
            dtos.add(new CatDto(cat));
        }
        return dtos;
    }

    @Override
    @Transactional
    public List<CatDto> getFreeCats() {
        List<CatDto> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAll()) {
            if (cat.getOwner() == null) {
                cats.add(new CatDto(cat));
            }
        }
        return cats;
    }

    @Override
    @Transactional
    public void makeFriends(CatDto first_cat, CatDto second_cat) throws DaoException {
        if (first_cat == null || second_cat == null) {
            throw new NullPointerException("Cats cannot be nulls");
        }
        Cat cat1 = dtoConverter.asEntity(first_cat);
        Cat cat2 = dtoConverter.asEntity(second_cat);
        cat1.addFriend(cat2);
        cat2.addFriend(cat1);

        update(first_cat);
        update(second_cat);
    }
}
