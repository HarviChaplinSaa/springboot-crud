package com.felipe.curso.springboot.app.springboot_crud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipe.curso.springboot.app.springboot_crud.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);    
    
}
