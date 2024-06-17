package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "GetBooksByAuthorResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetBooksByAuthorResponse implements BaseResponse {
    @XmlElement
    private List<BookDetail> books;

    private int errorCode;
    private String errorMessage;
    private String errorDetails;
    private int httpStatusCode;

    @Data

    public static class BookDetail {
        @XmlElement
        private Long id;
        @XmlElement
        private String bookTitle;
        @XmlElement
        private AuthorDetail author;
    }

    @Data
    public static class AuthorDetail {
        @XmlElement
        private Long id;
        @XmlElement
        private String firstName;
        @XmlElement
        private String secondName;
        @XmlElement
        private String familyName;
    }
}