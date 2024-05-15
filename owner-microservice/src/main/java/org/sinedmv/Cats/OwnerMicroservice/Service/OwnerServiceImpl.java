package org.sinedmv.Cats.OwnerMicroservice.Service;

import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.DtoConverter;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
import org.sinedmv.Cats.OwnerMicroservice.Dao.OwnerRepository;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Models.Cat;
import org.sinedmv.Cats.Entities.Models.Owner;
import org.sinedmv.Cats.OwnerMicroservice.RabbitMQ.Queues;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    public static final String EXCHANGE_NAME = "catExchange";
    private OwnerRepository ownerRepository;
    private DtoConverter dtoConverter;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, RabbitTemplate rabbitTemplate) {
        this.ownerRepository = ownerRepository;
        this.dtoConverter = new DtoConverter();
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    @Transactional
    public void add(OwnerDto ownerDto) throws DaoException {
        if (ownerDto == null) {
            throw new NullPointerException("Owner cannot be null");
        }
        if (ownerRepository.existsById(ownerDto.getId())) {
            throw new DaoException("Owner with this ID is already exists:" + ownerDto.getId());
        }
        ownerRepository.save(dtoConverter.asEntity(ownerDto));
    }

    @Override
    @Transactional
    public void update(OwnerDto ownerDto) throws DaoException {
        if (ownerDto == null) {
            throw new NullPointerException("Owner cannot be null");
        }
        if (ownerRepository.existsById(ownerDto.getId())) {
            throw new DaoException("Owner with this ID doesn't exist:" + ownerDto.getId());
        }
        ownerRepository.save(dtoConverter.asEntity(ownerDto));
    }

    @Override
    @Transactional
    public void delete(Integer id) throws DaoException {
        if (ownerRepository.existsById(id)) {
            throw new DaoException("Owner with this ID doesn't exist:" + id);
        }
        ownerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OwnerDto getById(Integer id) throws DaoException {
        var owner = ownerRepository.findOwnerById(id);
        if (owner.isEmpty()) {
            throw new DaoException("Owner with this ID doesn't exist:" + id);
        }
        return new OwnerDto(owner.get());
    }

    @Override
    @Transactional
    public List<OwnerDto> getByName(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAllByName(name)) {
            owners.add(new OwnerDto(owner));
        }
        return owners;
    }

    @Override
    @Transactional
    public List<OwnerDto> getAll() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerRepository.findAll()) {
            owners.add(new OwnerDto(owner));
        }
        return owners;
    }

    @Override
    @Transactional
    public List<CatDto> getCatsByOwner(Integer id) {
        List<CatDto> cats = new ArrayList<>();
        try {
            for (CatDto cat : (List<CatDto>) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, Queues.GET_ALL_CATS_QUEUE, "")) {
                if (cat.getOwner().getId() == id) {
                    cats.add(cat);
                }
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return cats;
    }
}
