package com.ahmetaksunger.BlogAPI.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBlogByIdResponse {

    private String title;

    private String body;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    private List<GetBlogCommentsResponse> comments;

}
