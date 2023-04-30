package com.ahmetaksunger.BlogAPI.services.abstracts;

import com.ahmetaksunger.BlogAPI.dto.requests.AddCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogCommentsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.GetCommentByIdResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyCommentsResponse;

import java.util.List;

public interface CommentService {

    void add(AddCommentRequest addCommentRequest, Long blogId);
    void update(UpdateCommentRequest updateCommentRequest, Long commentId);

    void delete(Long id);

    List<GetBlogCommentsResponse> getBlogComments(Long blogId);

    GetCommentByIdResponse getCommentById(Long commentId);

    List<UserGetMyCommentsResponse> getMyComments();
}
