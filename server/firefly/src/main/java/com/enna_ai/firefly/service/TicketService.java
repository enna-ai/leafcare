package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Ticket;

import java.util.UUID;

public interface TicketService {

    Ticket createNewTicket(Ticket ticket);

    Ticket getTicketById(UUID id);

    Ticket updateTicketById(UUID id, Ticket updatedTicket);

    void deleteTicketById(UUID id);

    void deleteAllTickets();
}
