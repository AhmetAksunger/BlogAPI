package com.ahmetaksunger.BlogAPI.utils.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super(ErrorMessages.COMMENT_NOT_FOUND.message());
    }
}
