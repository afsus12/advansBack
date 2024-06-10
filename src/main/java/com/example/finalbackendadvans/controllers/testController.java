package com.example.finalbackendadvans.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff")
public class testController {
    @PreAuthorize("hasRole('ROLE_CC')")
 @GetMapping("/dd")
public String welcome (){

     return "Test ttttt";
 }

}
