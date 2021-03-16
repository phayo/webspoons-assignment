package com.webspoons.assignment.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import com.webspoons.assignment.Service.SnippetService;
import com.webspoons.assignment.Service.dto.SnippetDTO;
import com.webspoons.assignment.web.rest.vm.SnippetResourceVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/snippet/")
public class SnippetResource {

    private final SnippetService snippetService;

    public SnippetResource(final SnippetService snippetService) {this.snippetService = snippetService;}

    @PostMapping
    public ResponseEntity<SnippetDTO> saveSnippet(@RequestBody final SnippetResourceVM snippetResourceVM) throws URISyntaxException {
        SnippetDTO snippetDTO = snippetResourceVM;
        snippetDTO.setExpiresTimeInSecs(snippetResourceVM.getExpiresIn());
        SnippetDTO savedSnippet = snippetService.createSnippet(snippetDTO);
        log.info("REST call to save snippet {}", snippetDTO);
        return ResponseEntity.created(new URI(savedSnippet.getUrl())).body(savedSnippet);
    }

    @GetMapping("/{name}")
    public ResponseEntity<SnippetDTO> getSnippetByName(@PathVariable("name") final String name){
        log.info("REST call to get snippet with name {}", name);
        Optional<SnippetDTO> foundSnippet = snippetService.findOneByName(name);
        return foundSnippet.map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    //@GetMapping("/{slug}")
    public ResponseEntity<SnippetDTO> getSnippetBySlug(@PathVariable final String slug){
        log.info("REST call to get snippet with name {}", slug);
        Optional<SnippetDTO> foundSnippet = snippetService.findOneByName(slug);
        return foundSnippet.map(ResponseEntity::ok)
                           .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
