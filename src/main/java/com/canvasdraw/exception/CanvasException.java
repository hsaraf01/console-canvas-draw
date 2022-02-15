package com.canvasdraw.exception;

public class CanvasException extends Exception {

    public CanvasException(final String message) {
        super(message);
    }

    public CanvasException(String message, Throwable cause) {
        super(message, cause);
    }
}
