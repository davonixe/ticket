package com.ennov.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.service.TicketService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@RestController
@NoArgsConstructor
@AllArgsConstructor
public class TicketController {
    
    @Autowired
    TicketService service;

    @GetMapping("/ticket")
    List<Ticket> allTickets() {
        return service.getAllTickets();
    }

    @GetMapping("/ticket/{id}")
    Ticket getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/ticket")
    Ticket newTicket(@RequestBody Ticket ticket) {
        return service.createNewTicket(ticket);
    }
    
    @PutMapping("/ticket/{id}")
    Ticket updateTicket(@RequestBody Ticket ticket, @PathVariable Long id) {
        return service.updateOrCreateTicket(ticket, id);
    }

    @DeleteMapping("/ticket/{id}")
    void deleteTicket(@PathVariable Long id) {
        service.deleteTicket(id);
    }
}
