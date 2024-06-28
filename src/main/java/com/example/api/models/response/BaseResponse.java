package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetBooksByAuthorResponse.class, name = "getBooksByAuthor"),
        @JsonSubTypes.Type(value = CreateBookResponse.class, name = "createBook")
})
public abstract class BaseResponse {
    private int errorCode;
    private String errorMessage;
    private String errorDetails;
}
