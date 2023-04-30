package com.ahmetaksunger.BlogAPI.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidUsernameException extends ValidationException {
    public InvalidUsernameException() {
        super(ErrorMessages.USERNAME_INVALID.message());
    }

    public InvalidUsernameException(String message) {
        super(message);
    }
}
