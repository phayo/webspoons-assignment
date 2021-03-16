package com.webspoons.assignment.Service;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.webspoons.assignment.Service.dto.SnippetDTO;
import com.webspoons.assignment.domain.Snippet;
import com.webspoons.assignment.repository.SnippetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SnippetService {
    private final SnippetRepository snippetRepository;

    @Value("${snippet.context.url}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    public SnippetService(final SnippetRepository snippetRepository) {this.snippetRepository = snippetRepository;}

    public SnippetDTO createSnippet(SnippetDTO snippetDTO){
        log.info("Saving snippet {}", snippetDTO);
        return toDto(snippetRepository.save(toEntity(snippetDTO)));
    }

    public Optional<SnippetDTO> findOneByName(final String name){
        Instant now = Instant.now();
        log.info("Getting snippet by name {} and expiry date before {}", name, now);
        return snippetRepository
                .findOneByNameAndExpiresAtAfter(name, now)
                .map(this::toDto)
                .map(this::createSnippet);
    }

    public Optional<SnippetDTO> findOneBySlug(final String slug){
        Instant now = Instant.now();
        log.info("Getting snippet by name {} and expiry date before {}", slug, now);
        return snippetRepository
                .findOneBySlugAndExpiresAtAfter(slug, now)
                .map(this::toDto)
                .map(this::createSnippet);
    }

    public List<SnippetDTO> findAll(){
        log.info("Getting all saved snippets");
        return snippetRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Snippet toEntity(SnippetDTO snippetDTO){
        String hostAddress = "";
//        try{
//            hostAddress = InetAddress.getLocalHost().getHostAddress() +":" + port;
//        }catch (Exception ex){
//            hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
//        }
        return Snippet.builder()
                .snippet(snippetDTO.getSnippet())
                .expiresAt(Instant.now().plusMillis(snippetDTO.getExpiresTimeInSecs() * 1000L))
                .expiresIn(snippetDTO.getExpiresTimeInSecs())
                .name(snippetDTO.getName())
                .id(snippetDTO.getId())
                .url(hostAddress + contextPath + snippetDTO.getName())
                .slug(slugify(snippetDTO.getName()))
                .build();
    }

    private String slugify(final String name) {
        return name.replace("\\s+", "-") + "-" + getRandomInt();
    }

    private int getRandomInt() {
        SecureRandom secureRandomGenerator;
        try {
            secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");

            // Get 128 random bytes
            byte[] randomBytes = new byte[128];
            secureRandomGenerator.nextBytes(randomBytes);

            //Get random integer in range
            return secureRandomGenerator.nextInt(99999);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }

        return getRandomInt();
    }

    private SnippetDTO toDto(Snippet snippet){
        return SnippetDTO.builder()
                .expiresAt(snippet.getExpiresAt())
                .expiresTimeInSecs(snippet.getExpiresIn())
                .name(snippet.getName())
                .url(snippet.getUrl())
                .id(snippet.getId())
                .snippet(snippet.getSnippet())
                .build();
    }
}
