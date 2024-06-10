package com.example.finalbackendadvans.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientSignUpRequest {



    private String username;

    private String password;


   private  String Customer;
   private  Date birthdate;



}
