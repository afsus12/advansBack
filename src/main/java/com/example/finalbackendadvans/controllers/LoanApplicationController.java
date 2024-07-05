package com.example.finalbackendadvans.controllers;


import com.example.finalbackendadvans.dto.ClientResponseDTO;
import com.example.finalbackendadvans.dto.LoanApplicationDTO;
import com.example.finalbackendadvans.dto.LoanValidationRequest;
import com.example.finalbackendadvans.dto.LonaApplicationDetailsDTO;
import com.example.finalbackendadvans.entities.Client.AdvansWallet;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;
import com.example.finalbackendadvans.entities.Client.Status;
import com.example.finalbackendadvans.entities.Loan;
import com.example.finalbackendadvans.entities.RepaymentMilestone;
import com.example.finalbackendadvans.entities.Staff;
import com.example.finalbackendadvans.repositories.*;
import com.example.finalbackendadvans.services.FileStorageService;
import com.example.finalbackendadvans.services.LoanApplicationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
        @RequestMapping("/api/v1/staff/loanapp")
public class LoanApplicationController {


    private final LoanApplicationRepository loanApplicationRepository;
    private  final StaffRepository  staffRepository;
    @Autowired
    private  FileStorageService fileStorageService;
    private final ClientRepository clientRepository;
    private  final LoanApplicationService   loanApplicationService;
    private  final  AdvansWalletRepository advansWalletRepository;
    private  final  LoanRepository   loanRepository;
    private  final  RepaymentMilstoneRepository repaymentMilstoneRepository;
    @Autowired
    public  LoanApplicationController(LoanApplicationRepository loanApplicationRepository, ClientRepository clientRepository, StaffRepository staffRepository, LoanApplicationService loanApplicationService, LoanRepository loanRepository, AdvansWalletRepository advansWalletRepository,RepaymentMilstoneRepository repaymentMilstoneRepository){
        this.loanApplicationRepository=loanApplicationRepository;
        this.clientRepository=clientRepository;
        this.staffRepository= staffRepository;
        this.loanApplicationService = loanApplicationService;
        this.loanRepository=loanRepository;
        this.advansWalletRepository=advansWalletRepository;
        this.repaymentMilstoneRepository=repaymentMilstoneRepository;


    }
    @GetMapping("/canApply/{clientId}")
    public ResponseEntity<Boolean> canClientApplyForLoan(@PathVariable Long clientId) {
        boolean hasActiveLoans = loanApplicationService.hasActiveLoanApplications(clientId);
        return ResponseEntity.ok(hasActiveLoans);
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
            @RequestParam("supportingDocument") List<MultipartFile> supportingDocuments,
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

        List<String> fileNames = supportingDocuments.stream()
                .map(file -> fileStorageService.storeFile(file))
                .collect(Collectors.toList());
       System.out.println(fileNames);
        loanApplication.setSupportingDocumentPaths(fileNames);
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
                 clientOptional.get().getLastname(),clientOptional.get().getProspect().getCustomer(),clientOptional.get().getStatus(), clientOptional.get().getPhone(),
                 clientOptional.get().getBirthday(), clientOptional.get().getCin()


         );

         return ResponseEntity.ok(dto);
     }
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/getAllClient/{id}")
    public ResponseEntity<?> getAllLoansByClient(
            @PathVariable Long id,
            @RequestParam(required = false) LoanApplication.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role"));

        Pageable pageable = PageRequest.of(page, size);
        Page<LoanApplication> loanApplicationsPage = loanApplicationRepository.findAllByClientAndStatus(client, status, pageable);

        if (loanApplicationsPage.hasContent()) {
            // Convert Page<LoanApplication> to Page<LoanApplicationDTO>
            Page<LoanApplicationDTO> loanApplicationsDTOPage = loanApplicationsPage.map(loanApplication ->
                    new LoanApplicationDTO(
                            loanApplication.getId(),
                            loanApplication.getStatus(),
                            loanApplication.getCustomer(),
                            loanApplication.getRequestedCreditAmount(),
                            loanApplication.getApplicationDate(),
                            loanApplication.getFullname(),
                            loanApplication.getProductName()
                    )
            );

            return ResponseEntity.ok(loanApplicationsDTOPage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No loan applications found.");
        }
    }
    @PreAuthorize("hasRole('ROLE_CC')")
    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAllLoansByStaff(
            @PathVariable Long id,
            @RequestParam(required = false) LoanApplication.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role"));

        Pageable pageable = PageRequest.of(page, size);
        Page<LoanApplication> loanApplicationsPage = loanApplicationRepository.findAllByStaffAndStatus(staff, status, pageable);

        if (loanApplicationsPage.hasContent()) {
            List<LoanApplicationDTO> loanApplicationsDTO = loanApplicationsPage.getContent().stream()
                    .map(loanApplication -> new LoanApplicationDTO(
                            loanApplication.getId(),
                            loanApplication.getStatus(),
                            loanApplication.getCustomer(),
                            loanApplication.getRequestedCreditAmount(),
                            loanApplication.getApplicationDate(),
                            loanApplication.getFullname(),
                            loanApplication.getProductName()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(loanApplicationsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No loan applications found.");
        }
    }



    @GetMapping("/get/{id}")
    public ResponseEntity<?> checkStatus(@PathVariable Long id) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findByIdWithSupportingDocuments(id);
        if (loanApplication.isPresent()) {
            // Load supporting documents as resources
            List<String> documentUrls = loanApplication.get().getSupportingDocumentPaths().stream()
                    .map(this::generateFileUrl)
                    .collect(Collectors.toList());


            // Example DTO construction with file resources
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
                    loanApplication.get().getClient().getId(),
                    documentUrls  // Pass loaded resources
            );

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found.");
        }

    }

    @PutMapping("/comite/validate/{id}")
    public ResponseEntity<String>  fullyvalidateLoanApplication(
            @PathVariable Long id,
            @RequestBody LoanValidationRequest request) {
        double amount = request.getAmount();
        int nbrofmonths = request.getNbrofmonths();
        double interest = request.getInterest();
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(id);
        if (optionalLoanApplication.isPresent()) {
            LoanApplication loanApplication = optionalLoanApplication.get();
            loanApplication.setStatus(LoanApplication.Status.FULLY_VERIFIED);

            // Save updated loan application status
            loanApplicationRepository.save(loanApplication);

            // Calculate repayment details
            double totalInterest = (amount * interest) / 100;
            double totalRepayment = amount + totalInterest;
            double monthlyRepayment = totalRepayment / nbrofmonths;

            // Create Loan entity
            Loan loan = new Loan();
            loan.setClient(loanApplication.getClient());
            loan.setLoanAmount(amount);
            loan.setInterestRate(interest);
            loan.setNumberOfMonthsToRepay(nbrofmonths);
            loan.setLoanApplication(loanApplication);
            loan.setLoanDate(LocalDate.now());
            loan.setDueDate(LocalDate.now().plusMonths(nbrofmonths));
            loan.setTotalAmount(totalRepayment);
            loan.setStatus(Loan.Status.IN_PROGRESS); // Set total amount including interest

            // Create and add repayment milestones
            loanRepository.save(loan);
            List<RepaymentMilestone> milestones = new ArrayList<>();
            for (int i = 0; i < nbrofmonths; i++) {
                RepaymentMilestone milestone = new RepaymentMilestone();
                milestone.setLoan(loan);
                milestone.setDueDate(LocalDate.now().plusMonths(i));
                milestone.setAmount(monthlyRepayment);
                milestone.setPaid(false);
                milestones.add(milestone);
            }
            repaymentMilstoneRepository.saveAll(milestones);

            // Save Loan entity


            // Create AdvansWallet entity for the client if they don't already have one
            Client client = loanApplication.getClient();
            Optional<AdvansWallet> existingWallet = advansWalletRepository.findByClient(client);
            if (existingWallet.isEmpty()) {
                AdvansWallet advansWallet = new AdvansWallet(client, generateUniqueAccountNumber(), 0.0);
                advansWalletRepository.save(advansWallet);
            } // Create AdvansWallet entity for the client

          client.setStatus(Status.ACTIVE);
            clientRepository.save(client);
            return ResponseEntity.ok("Loan application fully validated and processed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found.");
        }
    }

    // Method to generate a unique account number (for demonstration purposes)
    private String generateUniqueAccountNumber() {
        String shortId = RandomStringUtils.randomNumeric(11);// Logic to generate a unique account number (e.g., random or sequential logic)
        return "TND" + shortId; // Example: Generate a timestamp-based account number
    }
    @PreAuthorize("hasRole('ROLE_CC')")
    @PutMapping("/cc/validate/{id}")
    public ResponseEntity<String> validateLoanApplication(@PathVariable Long id, @RequestParam LoanApplication.Status status) {
        Optional<LoanApplication> optionalLoanApplication = loanApplicationRepository.findById(id);
        if (optionalLoanApplication.isPresent()) {
            LoanApplication loanApplication = optionalLoanApplication.get();
            loanApplication.setStatus(status);

            loanApplicationRepository.save(loanApplication);
            return ResponseEntity.ok("Loan application status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan application not found.");
        }
    }
    private String generateFileUrl(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/loans/documents/")
                .path(filename)
                .toUriString();
    }


}
