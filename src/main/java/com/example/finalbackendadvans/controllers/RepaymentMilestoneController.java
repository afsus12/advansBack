package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.dto.PayMileStoneDTO;
import com.example.finalbackendadvans.dto.RepaymentMilestoneDTO;
import com.example.finalbackendadvans.entities.RepaymentMilestone;
import com.example.finalbackendadvans.services.RepaymentMilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/milestones")
public class RepaymentMilestoneController {
    @Autowired
    private RepaymentMilestoneService milestoneService;



    @GetMapping("/loan/{loanId}")
    public Page<RepaymentMilestoneDTO> getMilestonesByLoanId(@PathVariable Long loanId,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return milestoneService.getMilestonesByLoanId(loanId, pageable);
    }
    @PostMapping("/payMilestone/{id}")
    public ResponseEntity<String> payMilestone(@PathVariable Long id,
                                               @RequestBody PayMileStoneDTO request) {
        try {
            boolean success = milestoneService.payMilestone(id, request.getUserId(), request.getAmount());
            if (success) {
                return ResponseEntity.ok("Milestone paid successfully");
            } else {
                return ResponseEntity.status(400).body("Payment failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
