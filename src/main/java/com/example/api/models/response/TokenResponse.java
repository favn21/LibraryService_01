package com.example.api.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class TokenResponse {
    @JsonProperty("token")
    private String token;
}
