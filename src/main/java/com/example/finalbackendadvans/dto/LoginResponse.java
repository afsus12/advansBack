package com.example.finalbackendadvans.dto;

import com.example.finalbackendadvans.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

	private Long  id;
	private String username;

	private String email;

	private String firstname;

	private String lastname;

	private String phone;

	private String password;


	private String jwt;

	private Role roles;



}
