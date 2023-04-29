package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetAllBlogsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;
import com.ahmetaksunger.BlogAPI.entity.Blog;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.BlogRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import com.ahmetaksunger.BlogAPI.utils.mappers.ModelMapperService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Data
public class BlogManager implements BlogService {

    private static final Logger logger = LogManager.getLogger(BlogManager.class);

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

        var b = blogRepository.save(blog);

        logger.info("Blog(id: " + b.getId() + ") has been added by " + username);
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
        logger.info("Blog(id: " + blog.getId() + ") has been updated by " + username);
    }

    @Override
    public void delete(Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("blog was not found"));

        if(!blog.getUser().equals(user)){
            throw new RuntimeException("blog was not found");
        }

        blogRepository.delete(blog);
        logger.info("Blog(id: " + blog.getId() + ") has been deleted by " + username);

    }

    @Override
    public List<UserGetMyBlogsResponse> getMyBlogs() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        List<Blog> blogs = blogRepository.findAllByUserId(user.getId());

        List<UserGetMyBlogsResponse> responses = new ArrayList<UserGetMyBlogsResponse>();

        for (Blog blog:blogs) {
            UserGetMyBlogsResponse response = mapperService.forResponse().map(blog, UserGetMyBlogsResponse.class);
            response.setCommentCount(blog.getComments().size());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public List<UserGetAllBlogsResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        List<UserGetAllBlogsResponse> responses = new ArrayList<UserGetAllBlogsResponse>();
        for (Blog blog: blogs) {
            UserGetAllBlogsResponse response = mapperService.forResponse().map(blog, UserGetAllBlogsResponse.class);
            response.setCommentCount(blog.getComments().size());
            responses.add(response);
        }

        return responses;
    }


}
