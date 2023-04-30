package com.ahmetaksunger.BlogAPI.services.rules;

import com.ahmetaksunger.BlogAPI.utils.exceptions.ErrorMessages;
import com.ahmetaksunger.BlogAPI.utils.exceptions.InvalidBlogTitleException;
import com.ahmetaksunger.BlogAPI.utils.exceptions.InvalidBlogBodyException;
import org.springframework.stereotype.Service;

@Service
public class BlogRules {

    public void isBlogTitleValid(String title){
        if(title == null || title.isEmpty()){
            throw new InvalidBlogTitleException(ErrorMessages.BLOG_TITLE_REQUIRED.message());
        } else if(title.length() > 100){
            throw new InvalidBlogTitleException(ErrorMessages.BLOG_TITLE_LONG.message());
        } else if(title.length() < 3){
            throw new InvalidBlogTitleException(ErrorMessages.BLOG_TITLE_SHORT.message());
        }
    }

    public void isBlogBodyValid(String body){
        if(body == null || body.isEmpty()){
            throw new InvalidBlogBodyException(ErrorMessages.BLOG_BODY_REQUIRED.message());
        } else if(body.length() > 5000){
            throw new InvalidBlogBodyException(ErrorMessages.BLOG_BODY_LONG.message());
        }
    }
}
