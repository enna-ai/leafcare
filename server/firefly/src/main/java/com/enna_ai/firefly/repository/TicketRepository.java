package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findTicketById(UUID id);
}
