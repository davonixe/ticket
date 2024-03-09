package com.ennov.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ennov.tickets.exception.ResourceNotFoundException;
import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.model.User;
import com.ennov.tickets.repo.TicketRepository;
import com.ennov.tickets.repo.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Afficher tous les utilisateurs");
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public User getUserById(Long id) {
        log.info("Afficher l'utilisateur avec id");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pas d'utilisateur avec id = " + id));
    }

    @Override
    public User createNewUser(User user) {
        log.info("Ajouter un nouveau utilisateur");
        return repository.save(new User(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getTickets()));
    }

    @SuppressWarnings("null")
    @Override
    public User updateUser(User user, Long id) {
        log.info("Mise a jour utilisateur");
        return repository.findById(id)
            .map(updateUser -> {
                    updateUser.setUsername(user.getUsername());
                    updateUser.setEmail(user.getEmail());
                    return repository.save(updateUser);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Pas d'utilisateur avec id = " + id));
            /*.orElseGet(() -> {
                user.setId(id);
                return repository.save(user);
            });*/
    }

    @SuppressWarnings("null")
    @Override
    public void deleteUser(Long id) {
        log.info("Suppression d'un utilisateur");
        repository.deleteById(id);
    }

    @SuppressWarnings("null")
    @Override
    public List<Ticket> getTicketsByUser(Long id) {
        User user = repository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Pas d'utilisateur avec id = " + id));
        return ticketRepository.findByUserId(user.getId());
    }

}
