package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.requests.AddBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateBlogRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogByIdResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetAllBlogsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;
import com.ahmetaksunger.BlogAPI.entity.Blog;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.BlogRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.BlogService;
import com.ahmetaksunger.BlogAPI.services.rules.BlogRules;
import com.ahmetaksunger.BlogAPI.utils.exceptions.BlogNotFoundException;
import com.ahmetaksunger.BlogAPI.utils.exceptions.ErrorMessages;
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
    private BlogRules rules;

    @Autowired
    public BlogManager(BlogRepository blogRepository, ModelMapperService mapperService,
                       UserRepository userRepository, BlogRules rules) {
        this.blogRepository = blogRepository;
        this.mapperService = mapperService;
        this.userRepository = userRepository;
        this.rules = rules;
    }

    @Override
    public void add(AddBlogRequest addBlogRequest) {

        rules.isBlogTitleValid(addBlogRequest.getTitle());
        rules.isBlogBodyValid(addBlogRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Blog blog = mapperService.forRequest().map(addBlogRequest,Blog.class);

        blog.setUser(user);
        blog.setCreatedAt(new Date());

        var b = blogRepository.save(blog);

        logger.info("Blog(id: " + b.getId() + ") has been added by " + username);
    }

    @Override
    public void update(UpdateBlogRequest updateBlogRequest, Long blogId) {

        rules.isBlogTitleValid(updateBlogRequest.getTitle());
        rules.isBlogBodyValid(updateBlogRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException());

        Date createdAt = blog.getCreatedAt();

        if(!blog.getUser().equals(user)){
            throw new BlogNotFoundException();
        }

        blog = mapperService.forRequest().map(updateBlogRequest,Blog.class);

        blog.setUpdatedAt(new Date());
        blog.setCreatedAt(createdAt);
        blog.setUser(user);
        blog.setId(blogId);
        blogRepository.save(blog);
        logger.info("Blog(id: " + blog.getId() + ") has been updated by " + username);
    }

    @Override
    public void delete(Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException());

        if(!blog.getUser().equals(user)){
            throw new BlogNotFoundException();
        }

        blogRepository.delete(blog);
        logger.info("Blog(id: " + blog.getId() + ") has been deleted by " + username);

    }

    @Override
    public List<UserGetMyBlogsResponse> getMyBlogs() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

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
    public List<UserGetAllBlogsResponse> getAllBlogs(String orderByField) {

        List<Blog> blogs = new ArrayList<>();

        if(orderByField.equals("title")){
            blogs = blogRepository.findAllByOrderByTitle();
        }else if(orderByField.equals("createdAt")){
            blogs = blogRepository.findAllByOrderByCreatedAtDesc();
        }else if(orderByField.equals("updatedAt")){
            blogs = blogRepository.findAllByOrderByUpdatedAtDesc();
        }else{
            blogs = blogRepository.findAll();
        }

        List<UserGetAllBlogsResponse> responses = new ArrayList<UserGetAllBlogsResponse>();
        for (Blog blog: blogs) {
            UserGetAllBlogsResponse response = mapperService.forResponse().map(blog, UserGetAllBlogsResponse.class);
            response.setCommentCount(blog.getComments().size());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public GetBlogByIdResponse getBlogById(Long blogId) {

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException());

        GetBlogByIdResponse response = mapperService.forResponse().map(blog,GetBlogByIdResponse.class);

        return response;
    }

    @Override
    public List<UserGetAllBlogsResponse> getBlogsByTitleLike(String title) {

        List<Blog> blogs = blogRepository.findAllByTitleContainingIgnoreCase(title);

        List<UserGetAllBlogsResponse> responses = new ArrayList<UserGetAllBlogsResponse>();

        for (Blog blog:blogs) {
            UserGetAllBlogsResponse response = mapperService.forResponse().map(blog, UserGetAllBlogsResponse.class);
            responses.add(response);
        }

        return responses;

    }


}
