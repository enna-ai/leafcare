package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.Comment;
import com.enna_ai.firefly.model.Ticket;
import com.enna_ai.firefly.repository.CommentRepository;
import com.enna_ai.firefly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<Comment> addCommentToTicket(@PathVariable UUID ticketId, @RequestBody Comment comment) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found: " + ticketId));
        comment.setTicket(ticket);
        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllCommentsForTicket(@PathVariable UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found:" + ticketId));

        List<Comment> comments = ticket.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        commentRepository.delete(comment);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<Comment> updateCommentById(@PathVariable UUID commentId, @RequestBody Comment updatedComment) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        comment.setContent(updatedComment.getContent());

        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.ok(savedComment);
    }
}
