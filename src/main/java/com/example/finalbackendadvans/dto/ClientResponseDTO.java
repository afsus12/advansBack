package com.example.finalbackendadvans.dto;

import com.example.finalbackendadvans.entities.Client.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    private String email;

    private String firstname;

    private String lastname;
   private  String customer;
   private Status status;
    private String phone;
    private Date birthday;
    private String cin;
}
