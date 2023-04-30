package com.ahmetaksunger.BlogAPI.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException() {
        super(ErrorMessages.USERNAME_INVALID.message());
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
