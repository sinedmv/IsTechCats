package org.sinedmv.Cats.OwnerMicroservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.DtoConverter;
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

@ExtendWith(MockitoExtension.class)
public class CatServiceTest {
    private CatService service;
    private CatRepository catRepository;
    private OwnerRepository ownerRepository;
    @Before
    public void setup() throws DaoException {
        MockitoAnnotations.initMocks(this);
        Cat cat1 = new Cat("t1", new Date(System.currentTimeMillis()), "Test1", Color.WHITE);
        Cat cat2 = new Cat("t2", new Date(System.currentTimeMillis()), "Test2", Color.BLACK);
        Cat cat3 = new Cat("t3", new Date(System.currentTimeMillis()), "Test3", Color.GRAY);
        Owner owner = new Owner();
        cat3.setOwner(owner);
        catRepository = mock(CatRepository.class);
        ownerRepository = mock(OwnerRepository.class);

        service = new CatServiceImpl(catRepository,new DtoConverter(catRepository, ownerRepository));
        when(catRepository.findById(1)).thenReturn(Optional.of(cat1));
        when(catRepository.findById(2)).thenReturn(Optional.of(cat2));
        when(catRepository.findById(3)).thenReturn(Optional.of(cat3));
        when(catRepository.findAll()).thenReturn(List.of(new Cat[]{cat1, cat2, cat3}));
    }

    @Test
    public void getAllCats() throws DaoException {
        List<CatDto> result = service.getAll();
        Assert.assertEquals("t1", result.get(0).getName());
        Assert.assertEquals("t2", result.get(1).getName());
        Assert.assertEquals("t3", result.get(2).getName());
    }

    @Test
    public void getById() throws DaoException {
        CatDto result = service.getById(2);
        Assert.assertEquals("t2", result.getName());
    }
}
