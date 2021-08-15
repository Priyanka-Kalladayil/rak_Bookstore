package com.rak.bookstore.payload;


import lombok.Data;

import java.time.LocalDateTime;

public @Data class ErrorResponse {

    private LocalDateTime dateTime;
    private String message;


}
