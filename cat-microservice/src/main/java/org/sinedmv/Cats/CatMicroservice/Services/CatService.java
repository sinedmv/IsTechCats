package org.sinedmv.Cats.CatMicroservice.Services;

import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Exceptions.InvalidStringAsEnumException;

import java.util.List;

public interface CatService {
    void add(CatDto catDto) throws DaoException;
    void update(CatDto catDto) throws DaoException;
    List<CatDto> getAll() throws DaoException;
    CatDto getById(Integer id) throws DaoException;
    List<CatDto> getByName(String name) throws DaoException;
    List<CatDto> getByBreed(String breed);
    List<CatDto> getByColor(String color) throws InvalidStringAsEnumException;
    List<CatDto> getFreeCats() throws DaoException;
    void delete(Integer id) throws DaoException;
    void makeFriends(CatDto first_cat, CatDto second_cat) throws DaoException;
}
