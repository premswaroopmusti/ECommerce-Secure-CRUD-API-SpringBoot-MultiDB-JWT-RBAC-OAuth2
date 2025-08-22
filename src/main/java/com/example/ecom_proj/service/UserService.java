package com.example.ecom_proj.service;

import com.example.ecom_proj.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.ecom_proj.repository.postgres.UserRepository;
import com.example.ecom_proj.model.postgres.Users;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users register(Users user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        return repo.save(user);
    }

    public Users admin_register(Users user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public String verify(Users user){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Fail";
    }


}
