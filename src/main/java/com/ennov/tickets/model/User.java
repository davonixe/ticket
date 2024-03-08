package com.ennov.tickets.model;


import java.util.List;

import com.ennov.tickets.model.audit.Auditable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class User extends Auditable<String> {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private UserRole role;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Ticket> tickets; 
    
}
