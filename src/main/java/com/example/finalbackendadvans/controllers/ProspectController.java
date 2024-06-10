package com.example.finalbackendadvans.controllers;


import com.example.finalbackendadvans.dto.CcSignUpRequest;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.services.ProspectService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/staff/prospect")
public class ProspectController {
    private final ProspectService prospectService;
    public  ProspectController(ProspectService prospectService){
        this.prospectService=prospectService;
    }
    @PreAuthorize("hasRole('ROLE_CC')")
    @PostMapping("/")
    public ResponseEntity<?> addProspect(@RequestBody Prospect prospect) throws MessagingException {

        return ResponseEntity.ok(prospectService.addProspect(prospect));

    }

    @PreAuthorize("hasRole('ROLE_CC')")
    @GetMapping("/{userCode}")
    public ResponseEntity<?> getAllProspect( @PathVariable Long userCode) {

        return ResponseEntity.ok(prospectService.getProspectsByStaffId(userCode));

    }

}
