package com.ennov.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.repo.TicketRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository repository;

    @Override
    public List<Ticket> getAllTickets() {
        log.info("Fetching all tickets");
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public Ticket getById(Long id) {
        log.info("Fetching a ticket by id");
        return repository.findById(id).get();
    }

    @Override
    public Ticket createNewTicket(Ticket ticket) {
        log.info("Create new ticket");
        return repository.save(ticket);
    }

    @Override
    public Ticket updateOrCreateTicket(Ticket ticket, Long id) {
        log.info("Mise a jour d'un ticket");
        return repository.findById(id)
            .map(updateTicket -> {
                    updateTicket.setTitle(ticket.getTitle());
                    updateTicket.setDescription(ticket.getDescription());
                    updateTicket.setStatus(ticket.getStatus());
                    updateTicket.setUser(ticket.getUser());
                    updateTicket.setAssignUser(ticket.getAssignUser());
                    return repository.save(updateTicket);
            })
            .orElseGet(() -> {
                ticket.setId(id);
                return repository.save(ticket);
            });
    }

    @SuppressWarnings("null")
    @Override
    public void deleteTicket(Long id) {
        log.info("Suppression d'un ticket");
        repository.deleteById(id);
    }

    @Override
    public Ticket assignTicket() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignTicket'");
    }
}
