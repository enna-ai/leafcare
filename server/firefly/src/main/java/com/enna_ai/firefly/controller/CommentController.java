package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.dto.CommentDto;
import com.enna_ai.firefly.dto.TicketDto;
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
@RequestMapping("/api/v1/tickets/{ticketId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<CommentDto> addCommentToTicket(@PathVariable UUID ticketId, @RequestBody CommentDto comment) {
        TicketDto ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found: " + ticketId));
        comment.setTicket(ticket);
        CommentDto savedComment = commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsForTicket(@PathVariable UUID ticketId) {
        TicketDto ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket ID not found:" + ticketId));

        List<CommentDto> comments = ticket.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable UUID commentId) {
        CommentDto comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable UUID commentId) {
        CommentDto comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        commentRepository.delete(comment);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable UUID commentId, @RequestBody CommentDto updatedComment) {
        CommentDto comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found:" + commentId));

        comment.setContent(updatedComment.getContent());

        CommentDto savedComment = commentRepository.save(comment);

        return ResponseEntity.ok(savedComment);
    }
}
