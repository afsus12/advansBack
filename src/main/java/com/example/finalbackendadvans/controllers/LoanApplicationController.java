package com.example.finalbackendadvans.controllers;


import com.example.finalbackendadvans.dto.ClientResponseDTO;
import com.example.finalbackendadvans.dto.LoanApplicationDTO;
import com.example.finalbackendadvans.dto.LonaApplicationDetailsDTO;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import com.example.finalbackendadvans.entities.Staff;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.LoanApplicationRepository;
import com.example.finalbackendadvans.repositories.StaffRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
        @RequestMapping("/api/v1/staff/loanapp")
public class LoanApplicationController {


    private final LoanApplicationRepository loanApplicationRepository;
    private  final StaffRepository  staffRepository;


    private final ClientRepository clientRepository;

    public  LoanApplicationController(LoanApplicationRepository loanApplicationRepository, ClientRepository clientRepository,StaffRepository staffRepository){
        this.loanApplicationRepository=loanApplicationRepository;
        this.clientRepository=clientRepository;
        this.staffRepository= staffRepository;
    }
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/apply")
    public ResponseEntity<String> applyForLoan(
            @RequestParam String nationalite,
            @RequestParam int age,
            @RequestParam String experienceDuration,
            @RequestParam String loanPurpose,
            @RequestParam String productName,
            @RequestParam String governorat,
            @RequestParam String activityAdresse,

            @RequestParam String fullName,
            @RequestParam String customer,
            @RequestParam String activityType,
            @RequestParam String previousMicrofinanceInstitution,
            @RequestParam double requestedCreditAmount,
            @RequestParam MultipartFile supportingDocument,
            @RequestParam Long clientId) {


        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not found.");
        }

        Client client = clientOptional.get();




        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setStaff(client.getProspect().getStaff());
        loanApplication.setNationalite(nationalite);
        loanApplication.setAge(age);
        loanApplication.setExperienceDuration(experienceDuration);
        loanApplication.setLoanPurpose(loanPurpose);
        loanApplication.setProductName(productName);
        loanApplication.setGovernorat(governorat);
        loanApplication.setActivityAdresse(activityAdresse);

        loanApplication.setFullname(fullName);
        loanApplication.setCustomer(customer);
        loanApplication.setActivitytype(activityType);
        loanApplication.setPreviousMicrofinanceInstitution(previousMicrofinanceInstitution);
        loanApplication.setRequestedCreditAmount(requestedCreditAmount);
        loanApplication.setStatus(LoanApplication.Status.IN_PROGRESS);
        loanApplication.setApplicationDate(new Date());
        loanApplication.setClient(client);
        try {
            loanApplication.setSupportingDocument(supportingDocument.getBytes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving document.");
        }

        loanApplicationRepository.save(loanApplication);

        return ResponseEntity.ok("Loan application submitted successfully.");
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")
     @GetMapping("/getClient/{id}")
     public ResponseEntity<?> getClient(@PathVariable Long id){
         Optional<Client> clientOptional = clientRepository.findById(id);
         if (!clientOptional.isPresent()) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client not found.");
         }
         ClientResponseDTO dto= new ClientResponseDTO(
                 clientOptional.get().getEmail(), clientOptional.get().getFirstname(),
                 clientOptional.get().getLastname(),clientOptional.get().getProspect().getCustomer(), clientOptional.get().getPhone(),
                 clientOptional.get().getBirthday(), clientOptional.get().getCin()


         );

         return ResponseEntity.ok(dto);
     }
    @PreAuthorize("hasRole('ROLE_CC')")
    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<LoanApplicationDTO>> getALlLoansByStaff(@PathVariable Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role"));
        List<LoanApplicationDTO> loanApplicationsDTO = new ArrayList<>();
     List<LoanApplication> loanApplications= loanApplicationRepository.getAllByStaff(staff);
        for (LoanApplication loanApplication : loanApplications) {
            LoanApplicationDTO dto = new LoanApplicationDTO(
                    loanApplication.getId(),
                    loanApplication.getStatus(),
                    loanApplication.getCustomer(),
                    loanApplication.getRequestedCreditAmount(),
                    loanApplication.getApplicationDate(),
                    loanApplication.getFullname()
            );
            loanApplicationsDTO.add(dto);
        }
        return ResponseEntity.ok(loanApplicationsDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> checkStatus(@PathVariable  Long id) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(id);
        if (loanApplication.isPresent()) {
            LonaApplicationDetailsDTO dto = new LonaApplicationDetailsDTO(
                    loanApplication.get().getId(),
                    loanApplication.get().getStatus(),
                    loanApplication.get().getCustomer(),
                    loanApplication.get().getRequestedCreditAmount(),
                    loanApplication.get().getApplicationDate(),
                    loanApplication.get().getFullname(),
                    loanApplication.get().getNationalite(),
                    loanApplication.get().getAge(),
                    loanApplication.get().getExperienceDuration(),
                    loanApplication.get().getLoanPurpose(),
                    loanApplication.get().getProductName(),
                    loanApplication.get().getGovernorat(),
                    loanApplication.get().getActivityAdresse(),
                    loanApplication.get().getActivitytype(),
                    loanApplication.get().getCreatedAt(),
                    loanApplication.get().getValidatedAt(),
                    loanApplication.get().getSupportingDocument()

            );

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found.");
        }
    }

    @PutMapping("/validate/{id}")
    public ResponseEntity<String> validateLoanApplication(@PathVariable Long id, @RequestParam LoanApplication.Status status) {
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(id);
        if (optionalLoanApplication.isPresent()) {
            LoanApplication loanApplication = optionalLoanApplication.get();
            loanApplication.setStatus(status);
            if (status == LoanApplication.Status.FULLY_VERIFIED || status == LoanApplication.Status.STEP1_VERIFIED) {
                loanApplication.setValidatedAt(new Date());
            }
            loanApplicationRepository.save(loanApplication);
            return ResponseEntity.ok("Loan application status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found.");
        }
    }


}
