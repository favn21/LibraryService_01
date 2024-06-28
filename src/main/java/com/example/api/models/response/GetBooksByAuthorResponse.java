package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetBooksByAuthorResponse extends BaseResponse {
    private List<BookDetail> books;

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