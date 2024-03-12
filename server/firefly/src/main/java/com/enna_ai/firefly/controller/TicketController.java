package com.enna_ai.firefly.controller;


import com.enna_ai.firefly.model.Email;
import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.model.User;
import com.enna_ai.firefly.repository.TicketRepository;
import com.enna_ai.firefly.service.EmailService;
import com.enna_ai.firefly.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final EmailService emailService;

    @Autowired
    private TicketRepository ticketRepository;

    public TicketController(TicketService ticketService, EmailService emailService) {
        this.ticketService = ticketService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createNewTicket(@RequestBody Ticket ticket, Authentication authentication) throws MessagingException {
        User user = (User) authentication.getPrincipal();
        String userEmailAddress = user.getEmail();
        String username = user.getUsername();

        Ticket createdTicket = ticketService.createNewTicket(ticket);
        String ticketId = createdTicket.getId().toString().substring(0, 6);

        String emailBody = "Hi " + username + ",\n\n" +
                "Your ticket (" + ticketId + ") has been created! " +
                "A staff member will get back to you as soon as possible.\n\n" +
                "Best Regards,\n" +
                "The LeafCare Team";

        Email email = new Email(userEmailAddress, "[LeafCare] Ticket Created Successfully!", emailBody);

        emailService.sendAutomatedEmail(email);

        return ResponseEntity.ok(createdTicket);
    }

    @GetMapping
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
