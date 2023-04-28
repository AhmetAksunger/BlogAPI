package com.ahmetaksunger.BlogAPI.controllers;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody AddBlogRequest addBlogRequest){
        blogService.add(addBlogRequest);

        return  ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @PutMapping("/")
    public ResponseEntity<UpdateBlogRequest> update(@RequestBody UpdateBlogRequest updateBlogRequest){
        blogService.update(updateBlogRequest);

        return ResponseEntity.ok(updateBlogRequest);
    }

}
