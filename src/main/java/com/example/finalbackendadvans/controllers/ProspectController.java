package com.example.finalbackendadvans.controllers;


import com.example.finalbackendadvans.dto.CcSignUpRequest;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.services.ProspectService;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getAllProspect(   @PathVariable Long userCode,
                                               @RequestParam(required = false, defaultValue = "") String firstname,
                                               @RequestParam(required = false, defaultValue = "") String lastname,
                                               @RequestParam(required = false, defaultValue = "") String customer,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Prospect> prospects = prospectService.getFilteredProspects(userCode,firstname, lastname, customer, pageable);
        return ResponseEntity.ok(prospects);

    }

}
