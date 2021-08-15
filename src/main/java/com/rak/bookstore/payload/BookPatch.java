package com.rak.bookstore.payload;

import lombok.Data;

public @Data class BookPatch {

    private String author;
    private Double price;
    private BookInfo.ClassificationEnum classification;
}
