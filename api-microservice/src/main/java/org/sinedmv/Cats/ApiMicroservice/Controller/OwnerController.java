package org.sinedmv.Cats.ApiMicroservice.Controller;

import org.sinedmv.Cats.ApiMicroservice.RabbitMQ.RabbitMQSender;
import org.sinedmv.Cats.ApiMicroservice.Models.MyUserDetails;
import org.sinedmv.Cats.Entities.Dto.CatDto;
import org.sinedmv.Cats.Entities.Dto.OwnerDto;
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
@RequestMapping("/owners")
public class OwnerController {
    private final RabbitMQSender sender;

    @Autowired
    public OwnerController(RabbitMQSender sender) {
        this.sender = sender;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOwner(@RequestBody @Valid OwnerDto ownerDto) {
        try {
            return new ResponseEntity<>(sender.sendAddOwner(ownerDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOwner(@RequestBody @Valid OwnerDto ownerDto) {
        try {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (ownerDto.getId() == userDetails.getOwnerId() || Objects.equals(userDetails.getRole(), "ROLE_ADMIN")) {
                return new ResponseEntity<>(sender.sendUpdateOwner(ownerDto), HttpStatus.OK);
            }
            return new ResponseEntity<>("You can only update your owner account", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOwner(@RequestBody @Positive Integer id) {
        try {
            return new ResponseEntity<>(sender.sendDeleteOwner(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable @Positive Integer id) {
        try {
            return new ResponseEntity<>(sender.sendGetOwnerById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getOwnersByName(@PathVariable @NotNull String name) {
        try {
            List<OwnerDto> owners = sender.sendGetOwnersByName(name);
            if (owners == null || owners.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(owners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOwners() {
        try {
            List<OwnerDto> owners = sender.sendGetAllOwners();
            if (owners == null || owners.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(owners, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{ownerId}/cats")
    public ResponseEntity<?> getCatsByOwner(@PathVariable @Positive Integer ownerId) {
        try {
            List<CatDto> cats = sender.sendGetCatsByOwner(ownerId);
            if (cats == null || cats.isEmpty()) {
                return new ResponseEntity<>("Список пуст. Либо один из микросервисов недоступен, либо список действительно пуст", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}