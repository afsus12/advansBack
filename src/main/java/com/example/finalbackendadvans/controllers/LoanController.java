package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.dto.LoanDTO;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Loan;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.LoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;

    public LoanController(LoanRepository loanRepository, ClientRepository clientRepository) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<LoanDTO>> getActiveLoansOfClient(@PathVariable Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            List<Loan> activeLoans = loanRepository.findByClientAndStatus(client, Loan.Status.IN_PROGRESS);

            // Map Loan entities to LoanDTOs
            List<LoanDTO> loanDTOs = activeLoans.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(loanDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to convert Loan entity to LoanDTO
    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setLoanAmount(loan.getLoanAmount());
        loanDTO.setInterestRate(loan.getInterestRate());
        loanDTO.setNumberOfMonthsToRepay(loan.getNumberOfMonthsToRepay());
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setDueDate(loan.getDueDate());
        loanDTO.setTotalAmount(loan.getTotalAmount());
        loanDTO.setStatus(loan.getStatus().toString());
        loanDTO.setLoanType(loan.getLoanApplication().getProductName());
        loanDTO.setPayedAmount(loan.getPayedAmount());
        loanDTO.setProductName(loan.getLoanApplication().getProductName());

        return loanDTO;
    }
}