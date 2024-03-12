package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.Message;
import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/messages")
public class MessageController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllCommentsFromTicket(@PathVariable UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));

        List<Message> messages = ticket.getMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable UUID ticketId, @PathVariable UUID messageId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));

        Message message = ticket.getMessages().stream()
                .filter(m -> m.getId().equals(messageId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Message ID not found."));

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Message> deleteMessageById(@PathVariable UUID ticketId, @PathVariable UUID messageId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));


        Optional<Message> messageOptional = ticket.getMessages().stream()
                .filter(m -> m.getId().equals(messageId))
                .findFirst();

        if (messageOptional.isPresent()) {
            ticket.getMessages().remove(messageOptional.get());
            ticketRepository.save(ticket);

            return ResponseEntity.noContent().build();
        } else {
            throw new NoSuchElementException("Message ID not found for this ticket.");
        }
    }
}
