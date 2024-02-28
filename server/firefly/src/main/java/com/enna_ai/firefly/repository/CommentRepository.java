package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, UUID> {

    Optional<CommentModel> findCommentModelByContent(String content);
}
