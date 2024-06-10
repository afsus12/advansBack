package com.example.finalbackendadvans.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class    CcSignUpRequest {

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String address;

}
