package com.ahmetaksunger.BlogAPI.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;
    private int age;

    public RegisterUserRequest(){
        super();
    }

    public RegisterUserRequest(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

}
