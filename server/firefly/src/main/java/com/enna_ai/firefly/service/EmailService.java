package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.Email;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendAutomatedEmail(Email email) throws MessagingException;
}
