package com.ahmetaksunger.BlogAPI.services.concretes;

import com.ahmetaksunger.BlogAPI.dto.responses.UserGetProfileResponse;
import com.ahmetaksunger.BlogAPI.dto.responses.UserGetMyBlogsResponse;
import com.ahmetaksunger.BlogAPI.entity.Blog;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.RoleRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.abstracts.UserService;
import com.ahmetaksunger.BlogAPI.utils.exceptions.ErrorMessages;
import com.ahmetaksunger.BlogAPI.utils.mappers.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserManager implements UserDetailsService, UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapperService mapperService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));
    }

    @Override
    public UserGetProfileResponse getProfile(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        UserGetProfileResponse response = mapperService.forResponse().map(user, UserGetProfileResponse.class);

        List<UserGetMyBlogsResponse> blogsResponses = new ArrayList<>();

        //manually mapping blogs.
        for (Blog blog:user.getBlogs()) {
            UserGetMyBlogsResponse blogResponse = new UserGetMyBlogsResponse();
            blogResponse.setTitle(blog.getTitle());
            blogResponse.setBody(blog.getBody());
            blogResponse.setCommentCount(blog.getComments().size());
            blogResponse.setCreatedAt(blog.getCreatedAt());
            blogsResponses.add(blogResponse);
        }

        response.setBlogs(blogsResponses);

        return response;
    }


}
