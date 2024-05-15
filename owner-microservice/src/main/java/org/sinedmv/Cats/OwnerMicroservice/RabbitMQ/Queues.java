package org.sinedmv.Cats.OwnerMicroservice.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Queues {
    public static final String ADD_OWNER_QUEUE = "add_owner_queue";
    public static final String UPDATE_OWNER_QUEUE = "update_owner_queue";
    public static final String DELETE_OWNER_QUEUE = "delete_owner_queue";
    public static final String GET_OWNER_BY_ID_QUEUE = "get_owner_by_id_queue";
    public static final String GET_OWNER_BY_NAME_QUEUE = "get_owner_by_name_queue";
    public static final String GET_ALL_OWNERS_QUEUE = "get_all_owners_queue";
    public static final String GET_CATS_BY_OWNER_QUEUE = "get_cats_by_owner_queue";
    public static final String LEAVE_CAT_QUEUE = "leave_cat_queue";
    public static final String TAKE_CAT_QUEUE = "take_cat_queue";
    public static final String GET_ALL_CATS_QUEUE = "getAllCatsQueue";

    @Bean
    public Queue addOwnerQueue() {
        return new Queue(ADD_OWNER_QUEUE);
    }

    @Bean
    public Queue updateOwnerQueue() {
        return new Queue(UPDATE_OWNER_QUEUE);
    }

    @Bean
    public Queue deleteOwnerQueue() {
        return new Queue(DELETE_OWNER_QUEUE);
    }

    @Bean
    public Queue getOwnerByIdQueue() {
        return new Queue(GET_OWNER_BY_ID_QUEUE);
    }

    @Bean
    public Queue getOwnerByNameQueue() {
        return new Queue(GET_OWNER_BY_NAME_QUEUE);
    }

    @Bean
    public Queue getAllOwnersQueue() {
        return new Queue(GET_ALL_OWNERS_QUEUE);
    }

    @Bean
    public Queue getCatsByOwnerQueue() {
        return new Queue(GET_CATS_BY_OWNER_QUEUE);
    }

    @Bean
    public Queue leaveCatQueue() {
        return new Queue(LEAVE_CAT_QUEUE);
    }

    @Bean
    public Queue takeCatQueue() {
        return new Queue(TAKE_CAT_QUEUE);
    }

    @Bean
    public Queue getAllCatsQueue() {
        return new Queue(GET_ALL_CATS_QUEUE);
    }

    @Bean
    public Binding bindingAddOwner(Queue addOwnerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(addOwnerQueue).to(exchange).with(ADD_OWNER_QUEUE);
    }

    @Bean
    public Binding bindingUpdateOwner(Queue updateOwnerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(updateOwnerQueue).to(exchange).with(UPDATE_OWNER_QUEUE);
    }

    @Bean
    public Binding bindingDeleteOwner(Queue deleteOwnerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteOwnerQueue).to(exchange).with(DELETE_OWNER_QUEUE);
    }

    @Bean
    public Binding bindingGetOwnerById(Queue getOwnerByIdQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getOwnerByIdQueue).to(exchange).with(GET_OWNER_BY_ID_QUEUE);
    }

    @Bean
    public Binding bindingGetOwnerByName(Queue getOwnerByNameQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getOwnerByNameQueue).to(exchange).with(GET_OWNER_BY_NAME_QUEUE);
    }

    @Bean
    public Binding bindingGetAllOwners(Queue getAllOwnersQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getAllOwnersQueue).to(exchange).with(GET_ALL_OWNERS_QUEUE);
    }

    @Bean
    public Binding bindingGetCatsByOwner(Queue getCatsByOwnerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getCatsByOwnerQueue).to(exchange).with(GET_CATS_BY_OWNER_QUEUE);
    }

    @Bean
    public Binding bindingLeaveCat(Queue leaveCatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(leaveCatQueue).to(exchange).with(LEAVE_CAT_QUEUE);
    }

    @Bean
    public Binding bindingTakeCat(Queue takeCatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(takeCatQueue).to(exchange).with(TAKE_CAT_QUEUE);
    }

    @Bean
    public Binding bindingGetAllCatsQueue(Queue getAllCatsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getAllCatsQueue).to(exchange).with(GET_ALL_CATS_QUEUE);
    }
}
