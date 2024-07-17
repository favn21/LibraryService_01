package com.example.api.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;



@Getter
public class TokenResponse {
    @JsonProperty("jwtToken")
    public String jwtToken;

}
