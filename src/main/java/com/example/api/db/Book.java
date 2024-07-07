package com.example.api.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Book {
    private long id;
    private String bookTitle;
    private LocalDateTime updated;
    private Author author;

}