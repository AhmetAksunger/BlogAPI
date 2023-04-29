package com.ahmetaksunger.BlogAPI.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequest {

    private Long blogId;

    private String body;

}
