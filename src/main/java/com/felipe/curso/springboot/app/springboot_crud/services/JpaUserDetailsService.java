package com.felipe.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipe.curso.springboot.app.springboot_crud.entities.User;
import com.felipe.curso.springboot.app.springboot_crud.repositories.UserRepository;


@Service
public class JpaUserDetailsService implements UserDetailsService{

    private final UserRepository repository;

    public JpaUserDetailsService(UserRepository repository){
        this.repository = repository;
    }

    @Transactional (readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s don't exist", username));
        }

        User user = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(),
            user.isEnable(),
            true,
            true,
            true,
            authorities 
        );

    }
    
}
