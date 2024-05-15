package org.sinedmv.Cats.ApiMicroservice.Service;

import org.sinedmv.Cats.ApiMicroservice.Models.ResponseDto;
import org.sinedmv.Cats.ApiMicroservice.Models.SignInDto;
import org.sinedmv.Cats.Entities.Dto.UserDto;
import org.sinedmv.Cats.ApiMicroservice.Exceptions.InvalidUserDataException;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;

import java.util.List;

public interface UserService {
    ResponseDto register(UserDto user) throws DaoException, InvalidUserDataException;
    ResponseDto login(SignInDto request);

    List<UserDto> getAll();

    UserDto getByUsername(String username) throws DaoException;
    UserDto getCurrent() throws DaoException;

    void deleteByUsername(String username) throws DaoException;
    void giveAdmin(int id);
}
