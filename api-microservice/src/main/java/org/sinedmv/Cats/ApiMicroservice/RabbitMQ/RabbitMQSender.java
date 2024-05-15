package org.sinedmv.Cats.ApiMicroservice.RabbitMQ;

import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableRabbit
@Component
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String sendAddCatMessage(CatDto catDto) {
        return (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.ADD_CAT_QUEUE, catDto);
    }

    public String sendUpdateCatMessage(CatDto catDto) {
        return (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.UPDATE_CAT_QUEUE, catDto);
    }

    public String sendDeleteCatMessage(Integer id) {
        return (String) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.DELETE_CAT_QUEUE, id);
    }

    public CatDto sendGetCatByIdMessage(Integer id) {
        return (CatDto) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_CAT_BY_ID_QUEUE, id);
    }

    public List<CatDto> sendGetCatsByNameMessage(String name) {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_CATS_BY_NAME_QUEUE, name);
    }

    public List<CatDto> sendGetCatsByBreedMessage(String breed) {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_CATS_BY_BREED_QUEUE, breed);
    }

    public List<CatDto> sendGetCatsByColorMessage(String color) {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_CATS_BY_COLOR_QUEUE, color);
    }

    public List<CatDto> sendGetAllCatsMessage() {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_ALL_CATS_QUEUE, "");
    }

    public List<CatDto> sendGetFreeCatsMessage() {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME, Queues.GET_FREE_CATS_QUEUE, "");
    }

    public String sendAddOwner(OwnerDto ownerDto) {
        return (String) rabbitTemplate.convertSendAndReceive(Queues.ADD_OWNER_QUEUE, ownerDto);
    }

    public String sendUpdateOwner(OwnerDto ownerDto) {
        return (String) rabbitTemplate.convertSendAndReceive(Queues.UPDATE_OWNER_QUEUE, ownerDto);
    }

    public String sendDeleteOwner(Integer id) {
        return (String) rabbitTemplate.convertSendAndReceive(Queues.DELETE_OWNER_QUEUE, id);
    }

    public OwnerDto sendGetOwnerById(Integer id) {
        return (OwnerDto) rabbitTemplate.convertSendAndReceive(RabbitMQConfig.EXCHANGE_NAME,Queues.GET_OWNER_BY_ID_QUEUE, id);
    }

    public List<OwnerDto> sendGetOwnersByName(String name) {
        return (List<OwnerDto>) rabbitTemplate.convertSendAndReceive(Queues.GET_OWNER_BY_NAME_QUEUE, name);
    }

    public List<OwnerDto> sendGetAllOwners() {
        return (List<OwnerDto>) rabbitTemplate.convertSendAndReceive(Queues.GET_ALL_OWNERS_QUEUE, "");
    }

    public List<CatDto> sendGetCatsByOwner(Integer id) {
        return (List<CatDto>) rabbitTemplate.convertSendAndReceive(Queues.GET_CATS_BY_OWNER_QUEUE, id);
    }
}
