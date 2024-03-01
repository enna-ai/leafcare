package com.enna_ai.firefly.dto;

import java.util.UUID;

public class TicketResDto {
    private UUID ticketId;
    private String responseBody;

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
