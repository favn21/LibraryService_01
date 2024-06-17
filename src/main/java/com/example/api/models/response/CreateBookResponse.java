package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBookResponse implements BaseResponse{
    private long bookId;
    private int errorCode;
    private String errorMessage;
    private String errorDetails;
    private int httpStatusCode;

}


