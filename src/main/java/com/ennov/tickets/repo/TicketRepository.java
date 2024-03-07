package com.ennov.tickets.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ennov.tickets.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
}
