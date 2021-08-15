package com.rak.bookstore.exception;

import com.rak.bookstore.payload.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handle404Exceptions(NotFoundException exception) {
        logger.error(" {}", exception);
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage(exception.getMessage());
        ResponseEntity<ErrorResponse> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return entity;
    }
    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultExceptions(EmptyResultDataAccessException exception) {
        logger.error(" {}", exception);
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("No Matching Record Found");
        ResponseEntity<ErrorResponse> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return entity;
    }

    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonExceptions(Exception exception) {
        logger.error(" {}", exception);
        ErrorResponse response = new ErrorResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Unexpected error occurred. Pleases check the data and try again.");
        ResponseEntity<ErrorResponse> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        logger.error(" {}", exception);
        StringBuffer errors = new StringBuffer();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.append(fieldName).append(" ").append(errorMessage).append(", ");
        });
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setMessage(errors.toString());
        ResponseEntity<ErrorResponse> entity = new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        return entity;
    }

}
