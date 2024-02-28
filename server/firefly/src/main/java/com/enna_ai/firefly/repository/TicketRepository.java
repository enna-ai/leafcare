package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel, UUID> {

    Optional<TicketModel> findTicketModelByUsername(String username);
}
