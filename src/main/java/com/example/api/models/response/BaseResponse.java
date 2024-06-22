package com.example.api.models.response;

public interface BaseResponse {
    int getErrorCode();
    String getErrorMessage();
    String getErrorDetails();
}
