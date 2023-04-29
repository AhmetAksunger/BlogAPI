package com.ahmetaksunger.BlogAPI.controllers;

import com.ahmetaksunger.BlogAPI.dto.requests.AddCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateCommentRequest;
import com.ahmetaksunger.BlogAPI.services.abstracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs/{blogId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody AddCommentRequest addCommentRequest, @PathVariable Long blogId){
        commentService.add(addCommentRequest,blogId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentRequest> update(@RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Long commentId){
        commentService.update(updateCommentRequest,commentId);

        return ResponseEntity.ok(updateCommentRequest);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> delete(@PathVariable Long id){
        commentService.delete(id);

        return ResponseEntity.ok("Deleted successfully");
    }
}
