package com.ennov.tickets.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ennov.tickets.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
