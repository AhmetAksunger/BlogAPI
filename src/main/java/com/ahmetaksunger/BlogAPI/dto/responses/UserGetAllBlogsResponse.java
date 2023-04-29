package com.ahmetaksunger.BlogAPI.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetAllBlogsResponse {

    private String title;

    private String body;

    private String username;

    private int commentCount;

    private Date createdAt;

}
