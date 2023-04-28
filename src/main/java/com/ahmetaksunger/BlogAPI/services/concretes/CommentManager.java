package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.services.abstracts.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
public class CommentManager implements CommentService {
}
