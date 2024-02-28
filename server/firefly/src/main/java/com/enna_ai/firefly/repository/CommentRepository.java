package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.dto.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentDto, UUID> {
}
