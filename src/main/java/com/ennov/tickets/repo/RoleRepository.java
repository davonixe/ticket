package com.ennov.tickets.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ennov.tickets.model.Role;
import com.ennov.tickets.model.UserRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(UserRole name);
}
