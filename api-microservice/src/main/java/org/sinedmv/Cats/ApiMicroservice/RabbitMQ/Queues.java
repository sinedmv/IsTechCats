package org.sinedmv.Cats.ApiMicroservice.RabbitMQ;

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
    public static final String ADD_CAT_QUEUE = "addCatQueue";
    public static final String UPDATE_CAT_QUEUE = "updateCatQueue";
    public static final String GET_ALL_CATS_QUEUE = "getAllCatsQueue";
    public static final String GET_CAT_BY_ID_QUEUE = "getCatByIdQueue";
    public static final String GET_CATS_BY_NAME_QUEUE = "getCatsByNameQueue";
    public static final String GET_CATS_BY_BREED_QUEUE = "getCatsByBreedQueue";
    public static final String GET_CATS_BY_COLOR_QUEUE = "getCatsByColorQueue";
    public static final String GET_FREE_CATS_QUEUE = "getFreeCatsQueue";
    public static final String DELETE_CAT_QUEUE = "deleteCatQueue";

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
    public Queue addCatQueue() {
        return new Queue(ADD_CAT_QUEUE);
    }

    @Bean
    public Queue updateCatQueue() {
        return new Queue(UPDATE_CAT_QUEUE);
    }

    @Bean
    public Queue getAllCatsQueue() {
        return new Queue(GET_ALL_CATS_QUEUE);
    }

    @Bean
    public Queue getCatByIdQueue() {
        return new Queue(GET_CAT_BY_ID_QUEUE);
    }

    @Bean
    public Queue getCatsByNameQueue() {
        return new Queue(GET_CATS_BY_NAME_QUEUE);
    }

    @Bean
    public Queue getCatsByBreedQueue() {
        return new Queue(GET_CATS_BY_BREED_QUEUE);
    }

    @Bean
    public Queue getCatsByColorQueue() {
        return new Queue(GET_CATS_BY_COLOR_QUEUE);
    }

    @Bean
    public Queue getFreeCatsQueue() {
        return new Queue(GET_FREE_CATS_QUEUE);
    }

    @Bean
    public Queue deleteCatQueue() {
        return new Queue(DELETE_CAT_QUEUE);
    }

    @Bean
    public Binding bindingAddCatQueue(Queue addCatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(addCatQueue).to(exchange).with(ADD_CAT_QUEUE);
    }

    @Bean
    public Binding bindingUpdateCatQueue(Queue updateCatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(updateCatQueue).to(exchange).with(UPDATE_CAT_QUEUE);
    }

    @Bean
    public Binding bindingGetAllCatsQueue(Queue getAllCatsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getAllCatsQueue).to(exchange).with(GET_ALL_CATS_QUEUE);
    }

    @Bean
    public Binding bindingGetCatByIdQueue(Queue getCatByIdQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getCatByIdQueue).to(exchange).with(GET_CAT_BY_ID_QUEUE);
    }

    @Bean
    public Binding bindingGetCatsByNameQueue(Queue getCatsByNameQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getCatsByNameQueue).to(exchange).with(GET_CATS_BY_NAME_QUEUE);
    }

    @Bean
    public Binding bindingGetCatsByBreedQueue(Queue getCatsByBreedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getCatsByBreedQueue).to(exchange).with(GET_CATS_BY_BREED_QUEUE);
    }

    @Bean
    public Binding bindingGetCatsByColorQueue(Queue getCatsByColorQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getCatsByColorQueue).to(exchange).with(GET_CATS_BY_COLOR_QUEUE);
    }

    @Bean
    public Binding bindingGetFreeCatsQueue(Queue getFreeCatsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(getFreeCatsQueue).to(exchange).with(GET_FREE_CATS_QUEUE);
    }

    @Bean
    public Binding bindingDeleteCatQueue(Queue deleteCatQueue, DirectExchange exchange) {
        return BindingBuilder.bind(deleteCatQueue).to(exchange).with(DELETE_CAT_QUEUE);
    }
}
