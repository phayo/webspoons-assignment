package com.webspoons.assignment.repository;

import java.time.Instant;
import java.util.Optional;

import com.webspoons.assignment.domain.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    Optional<Snippet> findOneByNameAndExpiresAtAfter(final String name, final Instant currentTime);
    Optional<Snippet> findOneBySlugAndExpiresAtAfter(final String slug, final Instant currentTime);
}
