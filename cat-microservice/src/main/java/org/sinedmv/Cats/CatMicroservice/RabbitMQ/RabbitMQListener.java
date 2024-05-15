package org.sinedmv.Cats.CatMicroservice.RabbitMQ;

import org.sinedmv.Cats.CatMicroservice.Services.CatServiceImpl;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Exceptions.InvalidStringAsEnumException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@EnableRabbit
public class RabbitMQListener {
    private CatServiceImpl catService;
    private String success = "Success";

    @Autowired
    public RabbitMQListener(CatServiceImpl catService) {
        this.catService = catService;
    }

    @RabbitListener(queues = Queues.ADD_CAT_QUEUE)
    public String addCat(@Payload CatDto catDto) {
        try {
            catService.add(catDto);
            return success;
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = Queues.UPDATE_CAT_QUEUE)
    public String updateCat(@Payload CatDto catDto) {
        try {
            catService.update(catDto);
            return success;
        } catch (DaoException e) {
            return e.getMessage();
        }
    }

    @RabbitListener(queues = Queues.GET_ALL_CATS_QUEUE)
    public List<CatDto> getAllCats() {
        return catService.getAll();
    }

    @RabbitListener(queues = Queues.GET_CAT_BY_ID_QUEUE)
    public CatDto getCatById(@Payload Integer id) {
        try {
            return catService.getById(id);
        } catch (DaoException e) {
            return null;
        }
    }

    @RabbitListener(queues = Queues.GET_CATS_BY_NAME_QUEUE)
    public List<CatDto> getCatByName(@Payload String name) {
        try {
            return catService.getByName(name);
        } catch (DaoException e) {
            return Collections.emptyList();
        }
    }

    @RabbitListener(queues = Queues.GET_CATS_BY_BREED_QUEUE)
    public List<CatDto> getCatsByBreed(@Payload String breed) {
        return catService.getByBreed(breed);
    }

    @RabbitListener(queues = Queues.GET_CATS_BY_COLOR_QUEUE)
    public List<CatDto> getCatsByColor(@Payload String color) {
        try {
            return catService.getByColor(color);
        } catch (InvalidStringAsEnumException e) {
            return Collections.emptyList();
        }
    }

    @RabbitListener(queues = Queues.GET_FREE_CATS_QUEUE)
    public List<CatDto> getFreeCats() {
        return catService.getFreeCats();
    }

    @RabbitListener(queues = Queues.DELETE_CAT_QUEUE)
    public String deleteCat(@Payload Integer id) {
        try {
            catService.delete(id);
            return success;
        } catch (DaoException e) {
            return e.getMessage();
        }
    }
}
