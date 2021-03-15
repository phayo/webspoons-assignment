package com.webspoons.assignment.Service.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnippetDTO {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long expiresTimeInSecs;
    @JsonAlias("expires_at")
    private Instant expiresAt;
    private String snippet;
    private String name;
    private String url;
    @JsonIgnore
    private String slug;

    private SnippetDTO(final Long id, final Long expiresTimeInSecs, final Instant expiresAt, final String snippet, final String name, final String url, final String slug) {
        this.id = id;
        this.expiresTimeInSecs = expiresTimeInSecs;
        this.expiresAt = expiresAt;
        this.snippet = snippet;
        this.name = name;
        this.url = url;
        this.slug = slug;
    }

    public SnippetDTO() {
    }
}
