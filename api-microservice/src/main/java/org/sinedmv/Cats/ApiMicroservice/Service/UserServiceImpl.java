package org.sinedmv.Cats.ApiMicroservice.Service;

import org.sinedmv.Cats.ApiMicroservice.Dao.UserRepository;
import org.sinedmv.Cats.ApiMicroservice.Models.MyUserDetails;
import org.sinedmv.Cats.ApiMicroservice.Models.ResponseDto;
import org.sinedmv.Cats.ApiMicroservice.Models.SignInDto;
import org.sinedmv.Cats.ApiMicroservice.Security.JwtService;
import org.sinedmv.Cats.ApiMicroservice.Security.MyUserDetailsService;
import org.sinedmv.Cats.Entities.Dto.*;
import org.sinedmv.Cats.Entities.Enums.Role;
import org.sinedmv.Cats.Entities.Exceptions.DaoException;
import org.sinedmv.Cats.Entities.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoConverter dtoConverter;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.dtoConverter = new DtoConverter();
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public ResponseDto register(UserDto userDto) throws DaoException {
        if (userDto == null) {
            throw new NullPointerException("UserDto cannot be null");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var user = dtoConverter.asEntity(userDto);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        MyUserDetails userDetails = new MyUserDetails(user);
        var jwt = jwtService.generateToken(userDetails);
        return new ResponseDto(jwt);
    }

    @Override
    public ResponseDto login(SignInDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userDetailsService.loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new ResponseDto(jwt);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(new UserDto(user));
        }
        return users;
    }

    @Override
    public UserDto getByUsername(String username) throws DaoException {
        if (username == null) {
            throw new NullPointerException("Username cannot be null");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new DaoException("Cannot find user with this username");
        }
        return new UserDto(user.get());
    }

    @Override
    public UserDto getCurrent() throws DaoException {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Override
    public void deleteByUsername(String username) throws DaoException {
        if (username == null) {
            throw new NullPointerException("Username cannot be null");
        }
        if (userRepository.existsByUsername(username)) {
            throw new DaoException("Cannot find user with this username");
        }
        userRepository.deleteByUsername(username);
    }

    @Override
    public void giveAdmin(int id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NullPointerException("User with this Id doesn't exist");
        }
        user.get().setRole(Role.ROLE_ADMIN);
        userRepository.save(user.get());
    }
}
