package com.enna_ai.firefly.service.ticket;

import com.enna_ai.firefly.dto.TicketDto;

import java.util.UUID;

public interface TicketService {
    TicketDto createTicket(TicketDto ticket);
    TicketDto getTicketById(UUID id);

    TicketDto updateTicketById(UUID id, TicketDto updatedTicket);
    void deleteTicketById(UUID id);
    void deleteAllTickets();
}
