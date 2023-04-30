package com.ahmetaksunger.BlogAPI.utils.exceptions;

public class BlogNotFoundException extends RuntimeException{
    public BlogNotFoundException() {
        super(ErrorMessages.BLOG_NOT_FOUND.message());
    }
}
