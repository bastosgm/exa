package com.exa.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.exa.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

// Esta classe é um tratador de excecoes, que intercepta as excecoes pra que essa classe possa fazer o tratamento
@ControllerAdvice
public class ResourceExceptionHandler {

    // metodo para interceptar a req que deu excecao do tipo
    // ResourceNotFoundException pra ele cair aqui e executar o q tiver dentro do
    // método resourceNotFound
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
            ResourceNotFoundException e,
            HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                error,
                e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
