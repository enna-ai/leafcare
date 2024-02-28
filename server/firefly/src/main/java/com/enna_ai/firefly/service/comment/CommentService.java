package com.enna_ai.firefly.service.comment;

import com.enna_ai.firefly.model.CommentModel;

import java.util.UUID;

public interface CommentService {
    CommentModel createComment(CommentModel comment);
    CommentModel getCommentById(UUID id);
}
