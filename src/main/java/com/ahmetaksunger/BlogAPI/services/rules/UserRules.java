package com.ahmetaksunger.BlogAPI.services.rules;

import com.ahmetaksunger.BlogAPI.repository.UserRepository;
import com.ahmetaksunger.BlogAPI.utils.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRules {

    @Autowired
    private UserRepository userRepository;

    public void checkIfEmailExists(String email){
        if(userRepository.existsByEmail(email)){
            throw new EmailExistsException();
        }
    }

    public void isPasswordValid(String password){
        if (password == null || password.isEmpty()){
            throw new InvalidUsernameException(ErrorMessages.PASSWORD_REQUIRED.message());
        }
        else if(password.length() < 5){
            throw new InvalidPasswordException(ErrorMessages.PASSWORD_SHORT.message());
        } else if(!password.matches(".*\\d.*")){
            throw new InvalidPasswordException(ErrorMessages.PASSWORD_NO_DIGIT.message());
        }
    }

    public void isUsernameValid(String username){
        if(username == null || username.isEmpty()){
            throw new InvalidUsernameException(ErrorMessages.USERNAME_REQUIRED.message());
        }
        else if(username.length() < 4){
            throw new InvalidUsernameException(ErrorMessages.USERNAME_SHORT.message());
        }else if(username.length() >= 20){
            throw new InvalidUsernameException(ErrorMessages.USERNAME_LONG.message());
        }else if(!username.matches("^[a-zA-Z0-9._-]{4,20}$")){
            throw new InvalidUsernameException();
        }
    }

    public void isEmailValid(String email) {
        if(email == null || email.isEmpty()) {
            throw new InvalidEmailException(ErrorMessages.EMAIL_REQUIRED.message());
        } else if(!email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+([\\w-]+\\.)+[\\w-]{2,}$")) {
            throw new InvalidEmailException(ErrorMessages.EMAIL_INVALID.message());
        }
    }

    public void isAgeValid(int age){
        if(age > 100 || age < 13){
            throw new InvalidAgeException();
        }
    }

}
