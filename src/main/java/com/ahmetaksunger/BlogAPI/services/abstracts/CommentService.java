package com.ahmetaksunger.BlogAPI.services.abstracts;

import com.ahmetaksunger.BlogAPI.dto.requests.AddCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateCommentRequest;

public interface CommentService {

    void add(AddCommentRequest addCommentRequest, Long blogId);
    void update(UpdateCommentRequest updateCommentRequest, Long commentId);

    void delete(Long id);

}
