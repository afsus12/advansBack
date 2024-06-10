package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.entities.StaffEntities.Opportunity;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.repositories.OpportunityRepository;
import com.example.finalbackendadvans.services.OpportunityService;
import com.example.finalbackendadvans.services.ProspectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/staff/opportunity")
@RestController
public class OpportunityController {

    private final OpportunityService opportunityService;
    private  final OpportunityRepository  opportunityRepository;
    public  OpportunityController(OpportunityService opportunityService,OpportunityRepository opportunityRepository){
        this.opportunityService=opportunityService;
        this.opportunityRepository=opportunityRepository;
    }
    @PreAuthorize("hasRole('ROLE_CC')")
    @PostMapping("/")
    public ResponseEntity<Prospect> addOpp(@RequestBody Opportunity opportunity) {
        if (opportunityRepository.existsByCustomer(opportunity.getCustomer())) {

            throw new IllegalArgumentException("Customer already exists");
        }
        return new ResponseEntity<>(opportunityService.addOpportunity(opportunity), HttpStatus.OK);
    }
}
