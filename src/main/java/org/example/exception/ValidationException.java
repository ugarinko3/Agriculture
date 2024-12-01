package org.example.exception;

/**
 * Ошибка валидации запроса
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
