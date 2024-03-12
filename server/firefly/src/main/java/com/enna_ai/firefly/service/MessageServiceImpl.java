package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Message;
import com.enna_ai.firefly.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    public Message getMessageById(UUID id) {
        return repository.findMessageById(id)
                .orElseThrow(() -> new NoSuchElementException("Message ID not found."));
    }

    public Message createMessage(Message message) {
        return repository.save(message);
    }
}
