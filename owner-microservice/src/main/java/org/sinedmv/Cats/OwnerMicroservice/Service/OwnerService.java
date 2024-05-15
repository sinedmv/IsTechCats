package org.sinedmv.Cats.OwnerMicroservice.Service;

import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Models.Owner;

import java.util.List;

public interface OwnerService {
    void add(OwnerDto ownerDto) throws DaoException;

    void update(OwnerDto ownerDto) throws DaoException;

    void delete(Integer id) throws DaoException;

    OwnerDto getById(Integer id) throws DaoException;
    List<OwnerDto> getByName(String name);

    List<OwnerDto> getAll() throws DaoException;
    List<CatDto> getCatsByOwner(Integer id) throws DaoException;
}
