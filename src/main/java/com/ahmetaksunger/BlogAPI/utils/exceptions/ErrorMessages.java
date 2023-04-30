package com.ahmetaksunger.BlogAPI.utils.exceptions;

public enum ErrorMessages {

    USERNAME_NOT_FOUND("Username was not found"),
    BLOG_NOT_FOUND("Blog was not found"),
    COMMENT_NOT_FOUND("Comment was not found"),
    EMAIL_EXISTS("Email already exists"),

    PASSWORD_INVALID("Invalid password"),

    PASSWORD_REQUIRED("Password is required"),
    PASSWORD_SHORT("Password must be longer than 5 characters"),
    PASSWORD_NO_DIGIT("Password must include at least 1 digit"),

    USERNAME_INVALID("Invalid username"),

    USERNAME_REQUIRED("Username is required"),
    USERNAME_SHORT("Username must be longer than 4 characters"),
    USERNAME_LONG("Username must be shorter than 20 characters"),

    EMAIL_REQUIRED("Email is required"),
    EMAIL_INVALID("Invalid email"),

    AGE_INVALID("Invalid age"),
    BLOG_TITLE_REQUIRED("Blog title is required"),
    BLOG_TITLE_SHORT("Blog title must be at least 3 characters"),
    BLOG_TITLE_LONG("Blog title can be at most 100 characters"),
    BLOG_BODY_REQUIRED("Blog body is required"),
    BLOG_BODY_LONG("Blog body can be at most 5000 characters"),
    COMMENT_BODY_REQUIRED("Comment body is required"),
    COMMENT_BODY_LONG("Comment body must be less 500 characters");
    private final String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String message(){
        return this.message;
    }
}
