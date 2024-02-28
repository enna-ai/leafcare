package com.enna_ai.firefly.service.comment;

import com.enna_ai.firefly.model.CommentModel;
import com.enna_ai.firefly.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentModel getCommentById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Comment ID not found."));
    }

    @Override
    public CommentModel createComment(CommentModel comment) {
        return commentRepository.save(comment);
    }
}
