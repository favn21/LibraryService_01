package com.example.api.models.response;

public interface BaseResponse {
    int getHttpStatusCode();
    int getErrorCode();
    String getErrorMessage();
    String getErrorDetails();
}
