package com.felipe.curso.springboot.app.springboot_crud.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data 
@Entity 
@Table (name = "users")
public class User {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
     
    private String password;

    @ManyToMany()
    @JoinTable(name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )
    private Set<Role> roles;

    @Transient
    private Boolean isAdmin;

    public User(){
        this.roles = new HashSet<>();
    }

    public User(Long id, String username, String password){
        this();
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
