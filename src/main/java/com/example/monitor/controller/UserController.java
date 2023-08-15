package com.example.monitor.controller;

import com.example.monitor.model.user.User;
import com.example.monitor.model.user.UserCreateDTO;
import com.example.monitor.model.user.UserDTO;
import com.example.monitor.service.ConversionService;
import com.example.monitor.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// todo use @responsestatus https://springdoc.org/#error-handling-for-rest-using-controlleradvice
@Controller
@RequestMapping(path = "user")
public class UserController {
    private final ConversionService conversionService;
    private final UserEntityService userEntityService;

    @Autowired
    public UserController(ConversionService conversionService, UserEntityService userEntityService) {
        this.conversionService = conversionService;
        this.userEntityService = userEntityService;
    }

    @GetMapping
    public @ResponseBody List<UserDTO> getAllMonitoredEndpoints() {
        return userEntityService.getAll()
                .stream()
                .map(conversionService::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserDTO createUser(@RequestBody UserCreateDTO userDTO) {
        User user = conversionService.convertToEntity(userDTO);
        user = userEntityService.create(user);
        return conversionService.convertToDTO(user);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody UserDTO getUserById(@PathVariable Long id) {
        User user = userEntityService.getById(id);
        return conversionService.convertToDTO(user);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody UserDTO updatUser(@PathVariable Long id, @RequestBody UserCreateDTO userDTO) {
        User user = userEntityService.getById(id);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user = userEntityService.update(user);
        return conversionService.convertToDTO(user);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteUserById(@PathVariable Long id) {
        userEntityService.deleteById(id);
    }
}

