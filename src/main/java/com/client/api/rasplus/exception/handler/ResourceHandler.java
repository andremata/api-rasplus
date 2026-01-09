package com.client.api.rasplus.exception.handler;

import com.client.api.rasplus.dto.error.ErrorResponseDTO;
import com.client.api.rasplus.exception.BadRequestException;
import com.client.api.rasplus.exception.BusinessException;
import com.client.api.rasplus.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> badRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> badRequestException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.CONFLICT)
                .statusCode(HttpStatus.CONFLICT.value())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> messages = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();

            messages.put(field, defaultMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> dataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
}
