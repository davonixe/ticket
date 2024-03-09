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
import com.ennov.tickets.model.User;
import com.ennov.tickets.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> userList = service.getAllUsers();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (User user : userList) {
                user.setTickets(service.getTicketsByUser(user.getId()));
            }
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = service.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> newUser(@Valid @RequestBody User user) {
        User _user = service.createNewUser(user);
        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        User _user = service.updateUser(user, id);
        return new ResponseEntity<>(_user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{id}/ticket")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable Long id) {
        List<Ticket> tickets = service.getTicketsByUser(id);
        return new ResponseEntity<>(tickets, HttpStatus.CREATED);
    }

}
