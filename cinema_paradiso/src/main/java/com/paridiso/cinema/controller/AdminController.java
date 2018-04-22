package com.paridiso.cinema.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paridiso.cinema.entity.CriticApplication;
import com.paridiso.cinema.security.JwtTokenGenerator;
import com.paridiso.cinema.security.JwtTokenValidator;
import com.paridiso.cinema.security.JwtUser;
import com.paridiso.cinema.entity.User;
import com.paridiso.cinema.service.implementation.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/admin")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminServiceImpl userService;

    @Autowired
    private JwtTokenGenerator generator;

    @Autowired
    private JwtTokenValidator validator;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/signup")
    public ResponseEntity<JwtUser> adminSignUp(@RequestBody User user) {
        User optionalUser = userService.signup(user).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "Admin ALREADY EXISTS"));
        JwtUser jwtUser = new JwtUser(optionalUser.getUsername(),
                generator.generate(optionalUser), optionalUser.getUserID(), optionalUser.getRole());
        return ResponseEntity.ok(jwtUser);
    }

    @RequestMapping(value = "/login", method = POST)
    public ResponseEntity<JwtUser> adminLogin(@RequestParam(value = "email", required = true) String email,
                                              @RequestParam(value = "password", required = true) String password) {
        User user = userService.login(email, password).orElseThrow(() ->
                new ResponseStatusException(BAD_REQUEST, "USER NOT FOUND"));
        JwtUser jwtUser = new JwtUser(user.getUsername(), generator.generate(user), user.getUserID(), user.getRole());
        return ResponseEntity.ok(jwtUser);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Boolean> adminLogout() {
        return null;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/suspend/{id}")
    public ResponseEntity<?> suspendUser(@PathVariable Integer id) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("success", userService.suspendUser(id));
        return ResponseEntity.ok(objectNode);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all_users")
    public ResponseEntity<List> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/all_applications")
    public ResponseEntity<List> getApplications() {
        return ResponseEntity.ok(userService.getAllApplications());
    }

    @PostMapping(value = "/verify/critic")
    public ResponseEntity<Boolean> verifyCritic(@RequestBody CriticApplication application) {
        userService.makeUserCritic(application.getUserId());
        return ResponseEntity.ok(true);
    }
}
