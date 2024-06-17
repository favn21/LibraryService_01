package com.example.api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateAuthorRequest {
    private String firstName;
    private String familyName;
    private String secondName;
}
