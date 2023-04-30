package com.ahmetaksunger.BlogAPI.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetMyCommentsResponse {

    private String body;
    private Date createdAt;
    private Date updatedAt;

}
