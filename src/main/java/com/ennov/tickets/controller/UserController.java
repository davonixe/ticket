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
import com.ennov.tickets.model.User;
import com.ennov.tickets.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    List<User> allUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    User getById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping("/users")
    User newUser(@RequestBody User user) {
        return service.createNewUser(user);
    }

    @PutMapping("users/{id}")
    User updateUser(@RequestBody User user, @PathVariable Long id) {
        return service.updateOrCreateUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @GetMapping("/users/{id}/ticket")
    List<Ticket> getTicketsByUser(@PathVariable Long id) {
        return service.getTicketsByUser(id);
    }

}
