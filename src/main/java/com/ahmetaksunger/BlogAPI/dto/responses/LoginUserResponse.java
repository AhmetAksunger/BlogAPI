package com.ahmetaksunger.BlogAPI.dto.responses;

public class LoginUserResponse {
    private String jwt;

    public LoginUserResponse(){
        super();
    }

    public LoginUserResponse(String jwt){
        this.jwt = jwt;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }

}
