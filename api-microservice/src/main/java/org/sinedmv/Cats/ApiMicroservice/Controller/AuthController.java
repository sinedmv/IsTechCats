package org.sinedmv.Cats.ApiMicroservice.Controller;

import org.sinedmv.Cats.ApiMicroservice.Models.SignInDto;
import org.sinedmv.Cats.ApiMicroservice.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid SignInDto auth) throws Exception {
        try {
            return new ResponseEntity<>(userService.login(auth), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
