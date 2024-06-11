package com.example.api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookRequest {
    private String bookTitle;
    private Author author;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Author {
        private Long id;

    }

}