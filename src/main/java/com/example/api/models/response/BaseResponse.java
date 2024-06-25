package com.example.api.models.response;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetBooksByAuthorResponse.class, name = "getBooksByAuthor"),
        @JsonSubTypes.Type(value = CreateBookResponse.class, name = "createBook")
})
public interface BaseResponse {

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    int getErrorCode();

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    String getErrorMessage();

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    String getErrorDetails();
}
