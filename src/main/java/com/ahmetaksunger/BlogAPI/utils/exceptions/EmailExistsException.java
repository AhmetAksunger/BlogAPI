package com.ahmetaksunger.BlogAPI.utils.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super(ErrorMessages.EMAIL_EXISTS.message());
    }
}
