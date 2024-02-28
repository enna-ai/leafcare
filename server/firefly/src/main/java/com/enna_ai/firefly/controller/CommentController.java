package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.CommentModel;
import com.enna_ai.firefly.model.TicketModel;
import com.enna_ai.firefly.repository.CommentRepository;
import com.enna_ai.firefly.repository.TicketRepository;
import com.enna_ai.firefly.service.comment.CommentService;
import com.enna_ai.firefly.service.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final TicketService ticketService;

    public CommentController(TicketService ticketService, CommentService commentService) {
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentModel> createComment(@PathVariable UUID ticketId, @RequestBody CommentModel comment) {
        TicketModel ticket = ticketService.getTicketById(ticketId);
        comment.setTicket(ticket);
        CommentModel savedComment = commentService.createComment(comment);

        return ResponseEntity.ok(savedComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentModel>> getAllCommentsForTicket(@PathVariable UUID ticketId) {
        TicketModel ticket = ticketService.getTicketById(ticketId);
        List<CommentModel> comments = ticket.getComments();

        return ResponseEntity.ok(comments);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentModel> getCommentById(@PathVariable UUID commentId) {
        CommentModel comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }
}
