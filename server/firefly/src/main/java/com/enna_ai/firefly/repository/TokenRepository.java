package com.enna_ai.firefly.repository;

import com.enna_ai.firefly.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("""
        select t from Token t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllTokensByUser(UUID userId);

    Optional<Token> findByToken(String token);
}
