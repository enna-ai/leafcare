package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @PostMapping
    public ResponseEntity<?> addTicket(@RequestBody Ticket ticket) {
        return new ResponseEntity<>(repository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Ticket>> getAllTickets() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found: " + id));

        return ResponseEntity.ok(ticket);
    }

    @GetMapping(params = {"title"})
    public ResponseEntity<Collection<Ticket>> getTicketByTitle(@RequestParam(value = "title") String title) {
        return new ResponseEntity<>(repository.findByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable UUID id) {
        repository.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllTickets() {
        repository.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicketById(@PathVariable UUID id, @RequestBody Ticket updatedTicket) {
        return repository.findById(id)
                .map(ticket -> {
                    Optional.ofNullable(updatedTicket.getCategory()).ifPresent(ticket::setCategory);
                    Optional.ofNullable(updatedTicket.getStatus()).ifPresent(ticket::setStatus);
                    Optional.ofNullable(updatedTicket.getPriority()).ifPresent(ticket::setPriority);

                    Ticket editedTicket = repository.save(ticket);
                    return ResponseEntity.ok(editedTicket);
                })
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found:" + id));
    }
}
