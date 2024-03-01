package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.dto.TicketResDto;
import com.enna_ai.firefly.model.EmailModel;
import com.enna_ai.firefly.model.TicketModel;
import com.enna_ai.firefly.repository.TicketRepository;
import com.enna_ai.firefly.service.email.EmailService;
import com.enna_ai.firefly.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;
    private final EmailService emailService;

    @Autowired
    private TicketRepository repository;

    public TicketController(TicketService ticketService, EmailService emailService) {
        this.ticketService = ticketService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<TicketModel> createTicket(@RequestBody TicketModel ticket) {
        TicketModel createdTicket = ticketService.createTicket(ticket);

        EmailModel emailModel = new EmailModel();
        emailModel.setSender("annelee3322@gmail.com");
        emailModel.setRecipient(ticket.getEmail());
        emailModel.setSubject("Ticket Created");
        emailModel.setBody("Your ticket has been successfully created!");

        emailService.sendEmail(emailModel);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PostMapping("/response")
    public ResponseEntity<String> sendTicketResponse(@RequestBody TicketResDto response) {
        UUID ticketId = response.getTicketId();
        String responseBody = response.getResponseBody();

        TicketModel ticket = ticketService.getTicketById(ticketId);
        String userEmail = ticket.getEmail();

        EmailModel emailModel = new EmailModel();
        emailModel.setSender("annelee3322@gmail.com");
        emailModel.setRecipient(userEmail);
        emailModel.setSubject("Response to your ticket");
        emailModel.setBody(responseBody);

        emailService.sendEmail(emailModel);

        return ResponseEntity.ok("Ticket response sent successfully.");
    }

    @GetMapping
    public ResponseEntity<Collection<TicketModel>> getAllTickets() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketModel> getTicketById(@PathVariable UUID id) {
        TicketModel ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TicketModel> deleteTicketById(@PathVariable UUID id) {
        ticketService.deleteTicketById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<TicketModel> deleteAllTickets() {
        ticketService.deleteAllTickets();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketModel> updateTicketById(@PathVariable UUID id, @RequestBody TicketModel updatedTicket) {
        TicketModel editedTicket = ticketService.updateTicketById(id, updatedTicket);
        return ResponseEntity.ok(editedTicket);
    }
}
