package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.requests.AddCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.requests.UpdateCommentRequest;
import com.ahmetaksunger.BlogAPI.dto.responses.GetBlogCommentsResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.GetCommentByIdResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyCommentsResponse;
import com.ahmetaksunger.BlogAPI.entity.Comment;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.BlogRepository;
import com.ahmetaksunger.BlogAPI.repository.CommentRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.CommentService;
import com.ahmetaksunger.BlogAPI.services.rules.CommentRules;
import com.ahmetaksunger.BlogAPI.utils.exceptions.BlogNotFoundException;
import com.ahmetaksunger.BlogAPI.utils.exceptions.CommentNotFoundException;
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
import java.util.List;

@Service
@Data
public class CommentManager implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentManager.class);

    private ModelMapperService mapperService;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private CommentRules rules;

    @Autowired
    public CommentManager(ModelMapperService mapperService, CommentRepository commentRepository,
                          UserRepository userRepository, BlogRepository blogRepository, CommentRules rules) {
        this.mapperService = mapperService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.rules = rules;
    }

    @Override
    public void add(AddCommentRequest addCommentRequest, Long blogId) {

        rules.isBodyValid(addCommentRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = mapperService.forRequest().map(addCommentRequest,Comment.class);

        comment.setBlog(blogRepository.findById(blogId).orElseThrow(()-> new BlogNotFoundException()));
        comment.setUser(user);

        var c = commentRepository.save(comment);

        logger.info(username + " has commented(id: " + c.getId() + ") on the blog(id: " + c.getBlog().getId() + ")");
    }

    @Override
    public void update(UpdateCommentRequest updateCommentRequest, Long commentId) {

        rules.isBodyValid(updateCommentRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());

        if(!comment.getUser().equals(user)){
            throw new CommentNotFoundException();
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

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = commentRepository.findById(id).orElseThrow(()->new CommentNotFoundException());

        if(!comment.getUser().equals(user)){
            throw new CommentNotFoundException();
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

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CommentNotFoundException());

        GetCommentByIdResponse response = mapperService.forResponse().map(comment, GetCommentByIdResponse.class);

        return response;

    }

    @Override
    public List<UserGetMyCommentsResponse> getMyComments() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        List<UserGetMyCommentsResponse> responses = new ArrayList<>();

        for (Comment comment:comments) {
            UserGetMyCommentsResponse response =mapperService.forResponse().map(comment,UserGetMyCommentsResponse.class);
            responses.add(response);
        }

        return responses;
    }
}
