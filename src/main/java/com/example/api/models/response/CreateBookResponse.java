package com.example.api.models.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookResponse extends BaseResponse {
    private Long bookId;
}

