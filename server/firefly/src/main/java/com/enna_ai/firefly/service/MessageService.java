package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Message;

import java.util.UUID;

public interface MessageService {

   Message getMessageById(UUID id);
}
