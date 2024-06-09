package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateAuthorResponse {
    private Long authorId;
    private Integer errorCode;
    private String errorMessage;
    private String errorDetails;
}