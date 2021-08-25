package com.example.demo.controller;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.MyRestResponseException;

@ControllerAdvice
public class SpringControllerErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { MyRestResponseException.class })
    protected ResponseEntity<Object> handleMyError(
      RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, null, HttpStatus.NOT_FOUND, request);
    }
}
