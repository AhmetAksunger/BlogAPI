package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.requests.AddCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogCommentsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.GetCommentByIdResponse;
import com.ahmetaksunger.BlogAPI.entity.Comment;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.BlogRepository;
import com.ahmetaksunger.BlogAPI.repository.CommentRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.CommentService;
import com.ahmetaksunger.BlogAPI.utils.mappers.ModelMapperService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class CommentManager implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentManager.class);

    private ModelMapperService mapperService;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private BlogRepository blogRepository;

    @Autowired
    public CommentManager(ModelMapperService mapperService, CommentRepository commentRepository, UserRepository userRepository, BlogRepository blogRepository) {
        this.mapperService = mapperService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public void add(AddCommentRequest addCommentRequest, Long blogId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Comment comment = mapperService.forRequest().map(addCommentRequest,Comment.class);

        comment.setBlog(blogRepository.findById(blogId).orElseThrow());
        comment.setUser(user);

        var c = commentRepository.save(comment);

        logger.info(username + " has commented(id: " + c.getId() + ") on the blog(id: " + c.getBlog().getId() + ")");
    }

    @Override
    public void update(UpdateCommentRequest updateCommentRequest, Long commentId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment was not found"));

        if(!comment.getUser().equals(user)){
            throw new RuntimeException("comment was not found");
        }

        var blog = comment.getBlog();

        comment = mapperService.forRequest().map(updateCommentRequest,Comment.class);

        comment.setUser(user);
        comment.setBlog(blog);
        comment.setId(commentId);
        commentRepository.save(comment);

        logger.info(username + " has edited their comment(id: " + comment.getId() + ") on the blog(id: " + blog.getId() + ")");
    }

    @Override
    public void delete(Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Comment comment = commentRepository.findById(id).orElseThrow(()->new RuntimeException("Comment was not found"));

        if(!comment.getUser().equals(user)){
            throw new RuntimeException("comment was not found");
        }

        commentRepository.delete(comment);
        logger.info(username + " has deleted their comment(id: " + id + ") on the blog(id: " + comment.getBlog().getId()
               + ")");
    }

    @Override
    public List<GetBlogCommentsResponse> getBlogComments(Long blogId) {

        List<Comment> comments = commentRepository.findAllByBlogId(blogId);
        List<GetBlogCommentsResponse> responses = new ArrayList<GetBlogCommentsResponse>();

        for (Comment comment:comments) {
            GetBlogCommentsResponse response = mapperService.forResponse().map(comment, GetBlogCommentsResponse.class);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public GetCommentByIdResponse getCommentById(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("comment was not found"));

        GetCommentByIdResponse response = mapperService.forResponse().map(comment, GetCommentByIdResponse.class);

        return response;

    }
}
