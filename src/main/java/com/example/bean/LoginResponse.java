package com.example.bean;

import lombok.Data;

@Data
public class LoginResponse {
    private Long userId;
    private String userEmail;
    private String displayName;
    private String companyName;
    private String accessToken;

}
