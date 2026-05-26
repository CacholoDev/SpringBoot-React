package com.appnotes.backend.exception;

public class NotasNotFoundException extends RuntimeException {

    public NotasNotFoundException(Long id) {
        super("No existe una nota con id " + id);
    }
}