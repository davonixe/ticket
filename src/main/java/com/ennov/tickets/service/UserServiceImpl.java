package com.ennov.tickets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.model.User;
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

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Override
    public User getUserById(Long id) {
        log.info("Fetching a user by id");
        return repository.findById(id).get();
    }

    @SuppressWarnings("null")
    @Override
    public User createNewUser(User user) {
        log.info("Create new user");
        return repository.save(user);
    }

    @Override
    public User updateOrCreateUser(User user, Long id) {
        log.info("Mise a jour utilisateur");
        return repository.findById(id)
            .map(updateUser -> {
                    updateUser.setUsername(user.getUsername());
                    updateUser.setEmail(user.getEmail());
                    return repository.save(updateUser);
            })
            .orElseGet(() -> {
                user.setId(id);
                return repository.save(user);
            });
    }

    @SuppressWarnings("null")
    @Override
    public void deleteUser(Long id) {
        log.info("Suppression d'un utilisateur");
        repository.deleteById(id);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long id) {
        User user = this.getUserById(id);
        throw new UnsupportedOperationException("Unimplemented method 'getTicketsByUser'");
    }

}
