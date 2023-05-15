package com.example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER_ACCOUNT")
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "access_token")
    private String accessToken;

}
