package com.enna_ai.firefly.service.ticket;

import com.enna_ai.firefly.dto.TicketDto;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public TicketDto getTicketById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found."));
    }

    @Override
    public TicketDto createTicket(TicketDto ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketById(UUID id) {
        getTicketById(id);
        ticketRepository.deleteById(id);
    }

    @Override
    public void deleteAllTickets() {
        ticketRepository.deleteAll();
    }

    @Override
    public TicketDto updateTicketById(UUID id, TicketDto updatedTicket) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    Optional.ofNullable(updatedTicket.getCategory()).ifPresent(ticket::setCategory);
                    Optional.ofNullable(updatedTicket.getStatus()).ifPresent(ticket::setStatus);
                    Optional.ofNullable(updatedTicket.getPriority()).ifPresent(ticket::setPriority);

                    return ticketRepository.save(ticket);
                })
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found." + id));
    }
}
