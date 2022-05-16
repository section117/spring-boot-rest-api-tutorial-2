package com.api.restapitutorial1.controller;

import com.api.restapitutorial1.dto.AddUserDTO;
import com.api.restapitutorial1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/add")
    public Object addUser(@RequestBody AddUserDTO addUserDTO) {
        return userService.addUser(
                addUserDTO.getName(),
                addUserDTO.getEmail(),
                addUserDTO.getPassword(),
                addUserDTO.getRole());
    }
}
