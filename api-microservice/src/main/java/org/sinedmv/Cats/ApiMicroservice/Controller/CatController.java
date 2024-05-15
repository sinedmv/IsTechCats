package org.sinedmv.Cats.ApiMicroservice.Controller;

import org.sinedmv.Cats.ApiMicroservice.Models.MyUserDetails;
import org.sinedmv.Cats.ApiMicroservice.RabbitMQ.RabbitMQSender;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final RabbitMQSender sender;

    @Autowired
    public CatController(RabbitMQSender sender) {
        this.sender = sender;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCat(@RequestBody @Valid CatDto catDto) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (catDto.getOwner().getId() != 0 && catDto.getOwner().getId() == userDetails.getOwnerId() && !Objects.equals(userDetails.getRole(), "ROLE_ADMIN")) {
            return new ResponseEntity<>("This cat has to be yours", HttpStatus.BAD_REQUEST);
        }
        try {
            String result = sender.sendAddCatMessage(catDto);
            if (result == null) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCat(@RequestBody @Valid CatDto catDto) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (catDto.getOwner().getId() != 0 && catDto.getOwner().getId() == userDetails.getOwnerId() && !Objects.equals(userDetails.getRole(), "ROLE_ADMIN")) {
            return new ResponseEntity<>("This cat has to be yours", HttpStatus.BAD_REQUEST);
        }
        try {
            String result = sender.sendUpdateCatMessage(catDto);
            if (result == null) {
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCatById(@PathVariable @Positive Integer id) {
        try {
            CatDto cat = sender.sendGetCatByIdMessage(id);
            if (cat == null) {
                return new ResponseEntity<>("Ошибка. Либо один из микросервисов недоступен, либо что-то не так в ваших данных", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getCatsByName(@PathVariable @NotNull String name) {
        try {
            List<CatDto> cats = sender.sendGetCatsByNameMessage(name);
            if (cats == null || cats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bybreed/{breed}")
    public ResponseEntity<?> getCatsByBreed(@PathVariable @NotNull String breed) {
        try {
            List<CatDto> cats = sender.sendGetCatsByBreedMessage(breed);
            if (cats == null || cats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bycolor/{color}")
    public ResponseEntity<?> getCatsByColor(@PathVariable @NotNull String color) {
        try {
            List<CatDto> cats = sender.sendGetCatsByColorMessage(color);
            if (cats == null || cats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCats() {
        try {
            List<CatDto> allCats = sender.sendGetAllCatsMessage();
            if (allCats == null || allCats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(allCats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/free")
    public ResponseEntity<?> getFreeCats() {
        try {
            List<CatDto> freeCats = sender.sendGetFreeCatsMessage();
            if (freeCats == null || freeCats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(freeCats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}