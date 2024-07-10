package com.example.api.db;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "updated")
    private LocalDateTime updated;

    @Column(name = "author_id")
    private long author_id;




}