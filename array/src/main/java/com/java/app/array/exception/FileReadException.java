package com.java.app.array.exception;

public class FileReadException extends Exception {
    private static final String DEFAULT_MESSAGE = "Error reading file: %s";

    public FileReadException(String message, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, message), cause);
    }

    public FileReadException(String message) {
        super(message);
    }
}