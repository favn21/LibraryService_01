package com.example.api.db;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Author {
    private Long id;
    private String firstName;
    private String secondName;
    private String familyName;

    public Author(Long id) {
        this.id = id;
    }
}

