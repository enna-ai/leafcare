package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.dto.TicketDto;
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
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketRepository repository;

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticket) {
        return new ResponseEntity<>(repository.save(ticket), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<TicketDto>> getAllTickets() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable UUID id) {
        TicketDto ticket = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found: " + id));

        return ResponseEntity.ok(ticket);
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
    public ResponseEntity<TicketDto> updateTicketById(@PathVariable UUID id, @RequestBody TicketDto updatedTicket) {
        return repository.findById(id)
                .map(ticket -> {
                    Optional.ofNullable(updatedTicket.getCategory()).ifPresent(ticket::setCategory);
                    Optional.ofNullable(updatedTicket.getStatus()).ifPresent(ticket::setStatus);
                    Optional.ofNullable(updatedTicket.getPriority()).ifPresent(ticket::setPriority);

                    TicketDto editedTicket = repository.save(ticket);
                    return ResponseEntity.ok(editedTicket);
                })
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found:" + id));
    }
}
