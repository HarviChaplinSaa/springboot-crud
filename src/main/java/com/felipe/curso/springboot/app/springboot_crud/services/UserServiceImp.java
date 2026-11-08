package com.felipe.curso.springboot.app.springboot_crud.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipe.curso.springboot.app.springboot_crud.entities.User;
import com.felipe.curso.springboot.app.springboot_crud.repositories.RoleRepository;
import com.felipe.curso.springboot.app.springboot_crud.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {

    private UserRepository repository;

    private RoleRepository roleRepository;

    private PasswordEncoder encoder;

    public UserServiceImp(
        UserRepository repository, 
        RoleRepository roleRepository,
        PasswordEncoder encoder
    ){
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        return repository.save(user);
    }
    
}
