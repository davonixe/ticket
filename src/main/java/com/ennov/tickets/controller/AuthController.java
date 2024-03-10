package com.ennov.tickets.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ennov.tickets.model.Role;
import com.ennov.tickets.model.User;
import com.ennov.tickets.model.UserRole;
import com.ennov.tickets.payload.LoginRequest;
import com.ennov.tickets.payload.MessageResponse;
import com.ennov.tickets.payload.SignupRequest;
import com.ennov.tickets.payload.UserInfoResponse;
import com.ennov.tickets.repo.RoleRepository;
import com.ennov.tickets.repo.UserRepository;
import com.ennov.tickets.service.security.UserDetailsImpl;
import com.ennov.tickets.service.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:8090", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser (@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        //return new ResponseEntity<?>(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles), jwtCookie.toString(), HttpStatus.OK);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                                    userDetails.getUsername(),
                                    userDetails.getEmail(),
                                    roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Le username existe deja!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Erreur: Ce email existe deja!"));
        }

        // Create new user's account
        User user = new User();

        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
        Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        } else {
        strRoles.forEach(role -> {
            switch (role) {
            case "admin":
            Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
            case "manager":
            Role modRole = roleRepository.findByName(UserRole.ROLE_MANAGER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
            default:
            Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            }
        });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistrer avec succes!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("Deconnexion!"));
    }
}
