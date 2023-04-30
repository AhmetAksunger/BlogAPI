package com.ahmetaksunger.BlogAPI.services.abstracts;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogByIdResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetAllBlogsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;

import java.util.List;

public interface BlogService {
    void add(AddBlogRequest addBlogRequest);

    void update(UpdateBlogRequest updateBlogRequest, Long blogId);

    void delete(Long id);

    List<UserGetMyBlogsResponse> getMyBlogs();

    List<UserGetAllBlogsResponse> getAllBlogs(String orderByField);

    GetBlogByIdResponse getBlogById(Long blogId);

    List<UserGetAllBlogsResponse> getBlogsByTitleLike(String title);

}
