package com.felipe.curso.springboot.app.springboot_crud.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipe.curso.springboot.app.springboot_crud.entities.Role;
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
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        
        Set<Role> roles = new HashSet<>();
        optionalRole.ifPresent(roles::add);
        
        if(user.isAdmin()){
            optionalRole = roleRepository.findByName("ROLE_ADMIN");
            optionalRole.ifPresent(roles::add);
        }

        user.setRoles(roles);

        user.setPassword(encoder.encode(user.getPassword()));

        return repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
    
}
