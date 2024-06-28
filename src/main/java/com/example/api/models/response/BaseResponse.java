package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "errorCode",
        visible = true
)
public abstract class BaseResponse {
    private int errorCode;
    private String errorMessage;
    private String errorDetails;
}
