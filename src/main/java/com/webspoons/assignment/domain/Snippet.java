package com.webspoons.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Snippet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Instant expiresAt;
    private Long expiresIn;
    private String snippet;
    private String name;
    private String slug;
    private String url;
}
