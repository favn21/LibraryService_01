package com.example.api.db;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "updated")
    private OffsetDateTime updated;

    @Column(name = "author_id")
    private Long author_id;
}