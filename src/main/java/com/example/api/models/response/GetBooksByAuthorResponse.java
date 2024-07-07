package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetBooksByAuthorResponse extends BaseResponse {
    @JsonProperty("books")
    private List<BookDetail> books;

    @Data
    @NoArgsConstructor
    public static class BookDetail {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("bookTitle")
        private String bookTitle;

        @JsonProperty("author")
        private AuthorDetail author;
    }

    @Data
    @NoArgsConstructor
    public static class AuthorDetail {
        @JsonProperty("id")
        private Long id;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("secondName")
        private String secondName;

        @JsonProperty("familyName")
        private String familyName;

        @JsonProperty("birthDate")
        private LocalDate birthDate;
    }
}