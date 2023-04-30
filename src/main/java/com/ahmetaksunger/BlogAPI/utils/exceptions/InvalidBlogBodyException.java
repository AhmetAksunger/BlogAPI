package com.ahmetaksunger.BlogAPI.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidBlogBodyException extends ValidationException {

    public InvalidBlogBodyException(String message) {
        super(message);
    }

}
