package com.ennov.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.service.TicketService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@CrossOrigin(origins = "http://localhost:8090")
@RestController
@NoArgsConstructor
@AllArgsConstructor
public class TicketController {
    
    @Autowired
    TicketService service;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> allTickets() {
        List<Ticket> tickeList = service.getAllTickets();
        if (tickeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tickeList, HttpStatus.OK);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable Long id) {
        Ticket ticket = service.getById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/tickets")
    public ResponseEntity<Ticket> newTicket(@Valid @RequestBody Ticket ticket) {
        Ticket _ticket = service.createNewTicket(ticket);
        return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
    }
    
    @PutMapping("/tickets/{id}")
    public ResponseEntity<Ticket> updateTicket(@Valid @RequestBody Ticket ticket, @PathVariable Long id) {
        Ticket _ticket = service.updateTicket(ticket, id);
        return new ResponseEntity<>(_ticket, HttpStatus.OK);
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        service.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/tickets/{id}/assign/{userId}")
    public ResponseEntity<Ticket> assignTicket(@PathVariable Long id, @PathVariable Long userId) {
        Ticket ticket = service.assignTicket(id, userId);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
}
