package com.ahmetaksunger.BlogAPI.controllers;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogByIdResponse;
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

    @GetMapping("/") //?orderBy={title,createdAt,updatedAt}
    public ResponseEntity<List<UserGetAllBlogsResponse>> getAllBlogs(@RequestParam(value = "orderBy",required = false) String orderByField){
        if(orderByField == null){
            orderByField = "createdAt";
        }
        return ResponseEntity.ok(blogService.getAllBlogs(orderByField));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<GetBlogByIdResponse> getBlogById(@PathVariable Long blogId){
        return ResponseEntity.ok(blogService.getBlogById(blogId));
    }

    @GetMapping("/search") //search?title="test"
    public ResponseEntity<List<UserGetAllBlogsResponse>> getBlogsByTitleLike(@RequestParam(value = "title", required = false) String title){
        return ResponseEntity.ok(blogService.getBlogsByTitleLike(title));
    }


}
