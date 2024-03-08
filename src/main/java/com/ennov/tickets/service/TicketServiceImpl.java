package com.ennov.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ennov.tickets.exception.ResourceNotFoundException;
import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.repo.TicketRepository;
import com.ennov.tickets.repo.UserRepository;

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

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Ticket> getAllTickets() {
        log.info("Afficher tous les tickers");
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public Ticket getById(Long id) {
        log.info("Afficher le ticket avec id ");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pas de ticket avec id = " + id));
    }

    @SuppressWarnings("null")
    @Override
    public Ticket createNewTicket(Ticket ticket) {
        log.info("Ajouter un nouveau ticket");
        return repository.save(new Ticket(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getStatus(), ticket.getUser()));
    }

    @SuppressWarnings("null")
    @Override
    public Ticket updateTicket(Ticket ticket, Long id) {
        log.info("Mise a jour d'un ticket");
        return repository.findById(id)
            .map(updateTicket -> {
                    updateTicket.setTitle(ticket.getTitle());
                    updateTicket.setDescription(ticket.getDescription());
                    updateTicket.setStatus(ticket.getStatus());
                    updateTicket.setUser(ticket.getUser());
                    return repository.save(updateTicket);
            }).orElseThrow(() -> new ResourceNotFoundException("Pas de ticket avec id = " + id));

            // Si besoin etendre la methode pour la mise a jour et CREATION
            /**.orElseGet(() -> {
                ticket.setId(id);
                return repository.save(ticket);
            });*/
    }

    @SuppressWarnings("null")
    @Override
    public void deleteTicket(Long id) {
        log.info("Suppression d'un ticket");
        repository.deleteById(id);
    }

    @SuppressWarnings("null")
    @Override
    public Ticket assignTicket(Long id, Long userId) {
        log.info("Assigner un ticket");
        return userRepository.findById(userId).map(user -> {
            Ticket ticket = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pas de ticket avec id = " + id));
            ticket.setUser(user);
            return repository.save(ticket);
        }).orElseThrow(() ->new ResourceNotFoundException("User " + userId + " not found"));
    }
}
