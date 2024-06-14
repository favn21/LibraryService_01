package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetBooksByAuthorResponse {
    @Getter
    private List<BookDetail> books;

    private Integer errorCode;
    private String errorMessage;
    private String errorDetails;
    private Integer httpStatusCode;

    @Data
    public static class BookDetail {
        private Long id;
        private String bookTitle;
        private AuthorDetail author;
    }

    @Data
    public static class AuthorDetail {
        private Long id;
        private String firstName;
        private String secondName;
        private String familyName;
    }
}