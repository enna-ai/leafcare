package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.enums.Role;
import com.enna_ai.firefly.model.Email;
import com.enna_ai.firefly.model.Message;
import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.model.User;
import com.enna_ai.firefly.repository.TicketRepository;
import com.enna_ai.firefly.service.EmailService;
import com.enna_ai.firefly.service.MessageServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/messages")
public class MessageController {

    private final EmailService emailService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService, EmailService emailService) {
        this.messageService = messageService;
        this.emailService = emailService;
    }

    private String getUserEmailAddress(Ticket ticket) {
        return ticket.getUser().getEmail();
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@PathVariable UUID ticketId, @RequestBody Message message, Authentication authentication) throws MessagingException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));

        User currentUser = (User) authentication.getPrincipal();

        Role respondentType = currentUser.getRole() == Role.ADMIN ? Role.ADMIN : Role.USER;

        boolean isResponse = message.getSubject().startsWith(("RE: "));

        String messageContent = isResponse ? message.getContent() : "RE: " + ticket.getSubject() + "\n\n" + message.getContent();

        message.setContent(messageContent);
        message.setRespondentType(respondentType);
        message.setUser(currentUser);
        message.setTicket(ticket);

        Message savedMessage = messageService.createMessage(message);

        ticket.setLastUpdatedAt(LocalDateTime.now());
        ticketRepository.save(ticket);

        if (respondentType == Role.ADMIN) {
            String userEmailAddress = getUserEmailAddress(ticket);
            String emailSubject = "RE: " + ticket.getSubject();
            String emailBody = "Hi " + currentUser.getUsername() + ",\n\n" +
                    "Your ticket (" + ticket.getId().toString().substring(0, 6) + ") has been updated." +
                    "Go to your inbox to view the updated information.\n\n" +
                    "Best Regards,\n" +
                    "The LeafCare Team";

            Email email = new Email(userEmailAddress, emailSubject, emailBody);

            emailService.sendAutomatedEmail(email);
        }

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping
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
