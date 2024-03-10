package com.ennov.tickets.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ennov.tickets.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    @Query(value = "SELECT * FROM ticket WHERE ticket.user_id = :id", nativeQuery = true)
    List<Object> FindByUser(@Param("id") Long id);
}
