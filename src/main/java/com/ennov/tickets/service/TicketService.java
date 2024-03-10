package com.ennov.tickets.service;

import java.util.List;

import com.ennov.tickets.model.Ticket;

public interface TicketService {
    public List<Ticket> getAllTickets();
    public Ticket getById(Long id);
    public Ticket createNewTicket(Ticket ticket);
    public Ticket updateTicket(Ticket ticket, Long id);
    public void deleteTicket(Long id);
    public Ticket assignTicket(Long id, Long userId);
}
