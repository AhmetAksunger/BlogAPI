package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.entity.Blog;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.BlogRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import com.ahmetaksunger.BlogAPI.utils.mappers.ModelMapperService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Data
public class BlogManager implements BlogService {

    private BlogRepository blogRepository;
    private ModelMapperService mapperService;
    private UserRepository userRepository;

    @Autowired
    public BlogManager(BlogRepository blogRepository, ModelMapperService mapperService, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.mapperService = mapperService;
        this.userRepository = userRepository;
    }

    @Override
    public void add(AddBlogRequest addBlogRequest) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Blog blog = mapperService.forRequest().map(addBlogRequest,Blog.class);

        blog.setUser(user);
        blog.setCreatedAt(new Date());

        blogRepository.save(blog);

    }

    @Override
    public void update(UpdateBlogRequest updateBlogRequest) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Blog blog = blogRepository.findById(updateBlogRequest.getId()).orElseThrow(() -> new RuntimeException("blog was not found"));

        Date createdAt = blog.getCreatedAt();

        if(!blog.getUser().equals(user)){
            throw new RuntimeException("no such a blog found");
        }

        blog = mapperService.forRequest().map(updateBlogRequest,Blog.class);

        blog.setUpdatedAt(new Date());
        blog.setCreatedAt(createdAt);
        blog.setUser(user);

        blogRepository.save(blog);


    }
}
