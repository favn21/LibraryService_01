package com.example.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateBookResponse extends BaseResponse {
    @JsonProperty("bookId")
    private Long bookId;
}

