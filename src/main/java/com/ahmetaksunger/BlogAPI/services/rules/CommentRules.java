package com.ahmetaksunger.BlogAPI.services.rules;

import com.ahmetaksunger.BlogAPI.utils.exceptions.ErrorMessages;
import com.ahmetaksunger.BlogAPI.utils.exceptions.InvalidCommentException;
import org.springframework.stereotype.Service;

@Service
public class CommentRules {

    public void isBodyValid(String body){
        if (body == null || body.isEmpty()){
            throw new InvalidCommentException(ErrorMessages.COMMENT_BODY_REQUIRED.message());
        } else if(body.length() > 500){
            throw new InvalidCommentException(ErrorMessages.COMMENT_BODY_LONG.message());
        }
    }

}
