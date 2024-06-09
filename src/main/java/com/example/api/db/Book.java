package com.example.api.db;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    private long id;
    private String bookTitle;
    private long authorId;

    public long getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}