package com.ahmetaksunger.BlogAPI.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidCommentException extends ValidationException {
    public InvalidCommentException(String message) {
        super(message);
    }
}
