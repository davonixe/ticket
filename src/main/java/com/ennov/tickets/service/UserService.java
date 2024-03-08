package com.ennov.tickets.service;

import java.util.List;

import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.model.User;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User createNewUser(User user);
    public User updateUser(User user, Long id);
    public void deleteUser(Long id);
    public List<Ticket> getTicketsByUser(Long id);
}
