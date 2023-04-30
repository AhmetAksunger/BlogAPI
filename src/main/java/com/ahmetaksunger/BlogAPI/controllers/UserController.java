package com.ahmetaksunger.BlogAPI.controllers;

import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyCommentsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetProfileResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import com.ahmetaksunger.BlogAPI.services.abstracts.CommentService;
import com.ahmetaksunger.BlogAPI.services.abstracts.UserService;
import com.ahmetaksunger.BlogAPI.services.concretes.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private BlogService blogService;
    private CommentService commentService;

    @Autowired
    public UserController(UserService userService, BlogService blogService, CommentService commentService) {
        this.userService = userService;
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserGetProfileResponse> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }

    @GetMapping("/my-blogs")
    public ResponseEntity<List<UserGetMyBlogsResponse>> getMyBlogs(){
        return ResponseEntity.ok(blogService.getMyBlogs());
    }

    @GetMapping("/my-comments")
    public ResponseEntity<List<UserGetMyCommentsResponse>> getMyComments(){
        return ResponseEntity.ok(commentService.getMyComments());
    }
}
