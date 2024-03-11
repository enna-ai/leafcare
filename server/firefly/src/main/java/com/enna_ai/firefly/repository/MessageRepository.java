package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    Optional<Message> findMessageById(UUID id);
}
