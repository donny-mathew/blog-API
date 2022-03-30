package com.code.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiError {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private String message;
    private String details;
    private Map<String, String> subErrors;

    private ApiError(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message, String details) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.details = details;
    }

    public ApiError(HttpStatus httpStatus, String details) {
        this();
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
