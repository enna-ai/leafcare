package com.enna_ai.firefly.service.ticket;

import com.enna_ai.firefly.dto.TicketResDto;
import com.enna_ai.firefly.model.TicketModel;

import java.util.UUID;

public interface TicketService {
    TicketModel createTicket(TicketModel ticket);
    TicketModel getTicketById(UUID id);

    TicketModel updateTicketById(UUID id, TicketModel updatedTicket);
    void deleteTicketById(UUID id);
    void deleteAllTickets();
}
