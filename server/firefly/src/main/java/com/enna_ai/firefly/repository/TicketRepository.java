package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.dto.TicketDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketDto, UUID> {

    Optional<TicketDto> findTicketDtoByUsername(String username);
}
