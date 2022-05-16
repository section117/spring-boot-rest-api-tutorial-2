package com.api.restapitutorial1.service;

import com.api.restapitutorial1.dao.UserRepository;
import com.api.restapitutorial1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Object addUser(String name, String email, String password, String role){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        userRepository.save(user);

        return "User Added successfully.";
    }
}
