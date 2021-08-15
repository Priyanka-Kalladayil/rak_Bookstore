package com.rak.bookstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
public @Data class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String category ;
    private String description ;
    private String authorName ;
    private String authorEmailAddress ;
    private Double price ;
    private String isbn ;
    private String classification;

}
