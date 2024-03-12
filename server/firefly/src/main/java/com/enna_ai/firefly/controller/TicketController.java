package com.enna_ai.firefly.controller;


import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.TicketRepository;
import com.enna_ai.firefly.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicketById(@PathVariable UUID id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<Ticket> deleteAllTickets() {
        ticketService.deleteAllTickets();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicketById(@PathVariable UUID id, @RequestBody Ticket updatedTicket) {
        Ticket editedTicket = ticketService.updateTicketById(id, updatedTicket);
        return ResponseEntity.ok(editedTicket);
    }
}
