package com.ahmetaksunger.BlogAPI.controllers;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetAllBlogsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody AddBlogRequest addBlogRequest){
        blogService.add(addBlogRequest);

        return  ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<UpdateBlogRequest> update(@RequestBody UpdateBlogRequest updateBlogRequest, @PathVariable Long blogId){
        blogService.update(updateBlogRequest, blogId);

        return ResponseEntity.ok(updateBlogRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        blogService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/my-blogs")
    public ResponseEntity<List<UserGetMyBlogsResponse>> getBlogs(){
        return ResponseEntity.ok(blogService.getMyBlogs());
    }

    @GetMapping("/")
    public ResponseEntity<List<UserGetAllBlogsResponse>> getAllBlogs(){
        return ResponseEntity.ok(blogService.getAllBlogs());
    }


}
