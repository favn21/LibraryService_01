package com.example.api.models.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
     @JsonProperty("login")
     private String login;

     @JsonProperty("password")
     private String password;
}
