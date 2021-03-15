package com.webspoons.assignment.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.webspoons.assignment.Service.dto.SnippetDTO;
import lombok.Data;

@Data
public class SnippetResourceVM extends SnippetDTO {
    @JsonAlias("expires_in")
    private Long expiresIn;

    @Override
    public Long getExpiresTimeInSecs(){
        return this.expiresIn;
    }
}
