package com.ennov.tickets.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ennov.tickets.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM ticket WHERE ticket.user_id = :id", nativeQuery = true)
    List<Object> FindByUser(@Param("id") Long id);
}
