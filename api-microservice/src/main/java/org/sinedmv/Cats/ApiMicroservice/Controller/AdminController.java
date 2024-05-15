package org.sinedmv.Cats.ApiMicroservice.Controller;

import org.sinedmv.Cats.Entities.Dto.UserDto;
import org.sinedmv.Cats.ApiMicroservice.Service.UserServiceImpl;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    @Autowired
    private UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody @Valid UserDto user) {
        try {
            service.register(user);
            return ResponseEntity.ok("User is successfully registered");
        } catch(DaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/giveAdmin/{id}")
    public ResponseEntity<?> giveAdmin(@PathVariable @Positive int id) {
        try {
            service.giveAdmin(id);
            return ResponseEntity.ok("Successfully gave admin");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
