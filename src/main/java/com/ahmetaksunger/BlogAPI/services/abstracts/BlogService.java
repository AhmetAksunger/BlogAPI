package com.ahmetaksunger.BlogAPI.services.abstracts;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;

public interface BlogService {
    void add(AddBlogRequest addBlogRequest);

    void update(UpdateBlogRequest updateBlogRequest);
}
