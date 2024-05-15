package org.sinedmv.Cats.OwnerMicroservice.RabbitMQ;

import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.OwnerMicroservice.Service.OwnerServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQListener {
    @Autowired
    private OwnerServiceImpl ownerService;

    @RabbitListener(queues = Queues.ADD_OWNER_QUEUE)
    public String addOwner(@Payload OwnerDto ownerDto) {
        try {
            ownerService.add(ownerDto);
            return "Success";
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = Queues.UPDATE_OWNER_QUEUE)
    public String updateOwner(@Payload OwnerDto ownerDto) {
        try {
            ownerService.update(ownerDto);
            return "Success";
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = Queues.DELETE_OWNER_QUEUE)
    public String deleteOwner(@Payload Integer id) {
        try {
            ownerService.delete(id);
            return "Success";
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = Queues.GET_OWNER_BY_ID_QUEUE)
    public OwnerDto getOwnerById(@Payload Integer id) {
        try {
            return ownerService.getById(id);
        } catch (DaoException e) {
            return null;
        }
    }

    @RabbitListener(queues = Queues.GET_OWNER_BY_NAME_QUEUE)
    public List<OwnerDto> getOwnerByName(@Payload String name) {
        return ownerService.getByName(name);
    }

    @RabbitListener(queues = Queues.GET_ALL_OWNERS_QUEUE)
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAll();
    }

    @RabbitListener(queues = Queues.GET_CATS_BY_OWNER_QUEUE)
    public List<CatDto> getCatsByOwner(@Payload Integer id) {
        return ownerService.getCatsByOwner(id);
    }
}
