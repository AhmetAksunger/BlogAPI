package com.ahmetaksunger.BlogAPI.services.concretes;

import java.util.HashSet;
import java.util.Set;

import com.ahmetaksunger.BlogAPI.dto.responses.LoginUserResponse;
import com.ahmetaksunger.BlogAPI.entity.Role;
import com.ahmetaksunger.BlogAPI.entity.User;
import com.ahmetaksunger.BlogAPI.repository.RoleRepository;
import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.services.rules.UserRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthenticationManager {

    private static final Logger logger = LogManager.getLogger(AuthenticationManager.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenService;

    @Autowired
    private UserRules rules;

    public User registerUser(String username, String password,String email,int age){

        //Rules
        rules.isUsernameValid(username);
        rules.isPasswordValid(password);
        rules.isEmailValid(email);
        rules.checkIfEmailExists(email);
        rules.isAgeValid(age);

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        User u = userRepository.save(new User(0L,username,encodedPassword,authorities,email,age));

        logger.info(u.getUsername() +"(id: " + u.getId() + ") has registered");

        return u;
    }

    public LoginUserResponse loginUser(String username, String password) throws RuntimeException {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginUserResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
