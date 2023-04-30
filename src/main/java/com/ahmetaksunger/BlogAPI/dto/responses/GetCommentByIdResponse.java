package com.ahmetaksunger.BlogAPI.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentByIdResponse {

    private String body;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    private String blogTitle;

}
