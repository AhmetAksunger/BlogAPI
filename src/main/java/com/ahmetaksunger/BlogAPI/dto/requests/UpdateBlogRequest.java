package com.ahmetaksunger.BlogAPI.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlogRequest {

    private String title;

    private String body;


}
