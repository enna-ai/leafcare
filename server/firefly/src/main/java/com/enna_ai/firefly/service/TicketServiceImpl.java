package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repository;

    @Autowired
    public TicketServiceImpl(TicketRepository repository) {
        this.repository = repository;
    }

    public Ticket createNewTicket(Ticket ticket) {
        return repository.save(ticket);
    }

    public Ticket getTicketById(UUID id) {
        return repository.findTicketById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));
    }

    public void deleteTicketById(UUID id) {
        getTicketById(id);
        repository.deleteById(id);
    }

    public void deleteAllTickets() {
        repository.deleteAll();
    }

    public Ticket updateTicketById(UUID id, Ticket updatedTicket) {
        return repository.findTicketById(id)
                .map(ticket -> {
                    Optional.ofNullable(updatedTicket.getCategory()).ifPresent(ticket::setCategory);
                    Optional.ofNullable(updatedTicket.getPriority()).ifPresent(ticket::setPriority);
                    Optional.ofNullable(updatedTicket.getStatus()).ifPresent(ticket::setStatus);

                    return repository.save(ticket);
                })
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));
    }
}
