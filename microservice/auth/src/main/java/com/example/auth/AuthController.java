package com.example.auth;

import com.example.auth.model.User;
import com.example.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    AuthRepository authRepository;

    @PostMapping("/auth")
    public boolean authenticate(@RequestBody User user){
        List<User> users = authRepository.findAll();

        for (User u: users) {
            if(u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }
}
