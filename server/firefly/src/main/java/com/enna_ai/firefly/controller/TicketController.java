package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @GetMapping
    public ResponseEntity<Collection<Ticket>> getAllTickets() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket) {
        return new ResponseEntity<>(repository.save(ticket), HttpStatus.CREATED);
    }
}
