package com.fernandocejas.android10.sample.domain.exception;

/**
 * Represents an exception thrown when invoking a method that does not exist.
 */
public final class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String description) {
        super(description);
    }
}
