package org.sinedmv.Cats.OwnerMicroservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.sinedmv.Cats.OwnerMicroservice.Service.OwnerService;
import org.sinedmv.Cats.OwnerMicroservice.Service.OwnerServiceImpl;
import org.sinedmv.Cats.Entities.Dto.DtoConverter;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
import org.sinedmv.Cats.Entities.Dao.CatRepository;
import org.sinedmv.Cats.OwnerMicroservice.Dao.OwnerRepository;
import org.sinedmv.Cats.Entities.Enums.Color;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Models.Cat;
import org.sinedmv.Cats.Entities.Models.Owner;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class OwnerServiceTest {
    private OwnerService service;
    private OwnerRepository ownerRepository;
    private CatRepository catRepository;
    @Before
    public void setup() throws DaoException {
        MockitoAnnotations.initMocks(this);
        Cat cat1 = new Cat("t1", new Date(System.currentTimeMillis()), "Test1", Color.WHITE);
        Cat cat2 = new Cat("t2", new Date(System.currentTimeMillis()), "Test2", Color.BLACK);
        Cat cat3 = new Cat("t3", new Date(System.currentTimeMillis()), "Test3", Color.GRAY);
        Owner owner1 = new Owner("w1", new Date(System.currentTimeMillis()));
        Owner owner2 = new Owner("w2", new Date(System.currentTimeMillis()));
        Owner owner3 = new Owner("w3", new Date(System.currentTimeMillis()));

        cat3.setOwner(owner1);
        ownerRepository = mock(OwnerRepository.class);
        catRepository = mock(CatRepository.class);

        service = new OwnerServiceImpl(ownerRepository, catRepository,new DtoConverter(catRepository, ownerRepository));
        when(ownerRepository.findById(1)).thenReturn(Optional.of(owner1));
        when(ownerRepository.findById(2)).thenReturn(Optional.of(owner2));
        when(ownerRepository.findById(3)).thenReturn(Optional.of(owner3));
        when(ownerRepository.findAll()).thenReturn(List.of(new Owner[]{owner1, owner2, owner3}));
    }

    @Test
    public void getAllOwners() throws DaoException {
        List<OwnerDto> result = service.getAll();
        Assert.assertEquals("w1", result.get(0).getName());
        Assert.assertEquals("w2", result.get(1).getName());
        Assert.assertEquals("w3", result.get(2).getName());
    }

    @Test
    public void getById() throws DaoException {
        OwnerDto result = service.getById(2);
        Assert.assertEquals("w2", result.getName());
    }
}
