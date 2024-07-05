package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.dto.RepaymentMilestoneDTO;
import com.example.finalbackendadvans.entities.Client.AdvansWallet;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.Transaction;
import com.example.finalbackendadvans.entities.Loan;
import com.example.finalbackendadvans.entities.RepaymentMilestone;
import com.example.finalbackendadvans.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepaymentMilestoneService {

    @Autowired
    private RepaymentMilstoneRepository milestoneRepository;
    @Autowired
    private AdvansWalletRepository  advansWalletRepository;
    @Autowired
    private ClientRepository     clientRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    public Page<RepaymentMilestoneDTO> getMilestonesByLoanId(Long loanId, Pageable pageable) {
        Page<RepaymentMilestone> milestones = milestoneRepository.findByLoanIdOrderByPaidAscDueDateAsc(loanId, pageable);
        return milestones.map(this::convertToDto);
    }

    @Transactional
    public boolean payMilestone(Long milestoneId, Long userId, double amount) throws Exception {
        Optional<RepaymentMilestone> milestoneOptional = milestoneRepository.findById(milestoneId);
        if (!milestoneOptional.isPresent()) {
            throw new EntityNotFoundException("Milestone not found");
        }
        RepaymentMilestone milestone = milestoneOptional.get();

        if (milestone.isPaid()) {
            throw new IllegalStateException("Milestone is already paid");
        }
        Client client = clientRepository.getClientById(userId);
        AdvansWallet wallet = advansWalletRepository.getAdvansWalletByClient(client);
        if (wallet == null) {
            throw new EntityNotFoundException("Wallet not found for user");
        }

        if (wallet.getBalance() < amount ) {
            throw new Exception("Insufficient funds in wallet");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        advansWalletRepository.save(wallet);

        milestone.setPaid(true);
        milestone.setPaidOn(LocalDate.now());
        milestoneRepository.save(milestone);
        Loan loan = loanRepository.getLoanById(milestone.getLoan().getId());
      double  payedAmount= loan.getPayedAmount();
         loan.setPayedAmount(payedAmount+amount);
         loanRepository.save(loan);
        Transaction transaction= new Transaction();
        transaction.setTranstype( Transaction.TransType.OUT);
        transaction.setType("Loan Milestone Payment");
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setClient(client);
        transactionRepository.save(transaction);


        return true;
    }
    private RepaymentMilestoneDTO convertToDto(RepaymentMilestone milestone) {
        RepaymentMilestoneDTO dto = new RepaymentMilestoneDTO();
        dto.setId(milestone.getId());
        dto.setPaid(milestone.isPaid());
        dto.setPaidOn(milestone.getPaidOn());
        dto.setDueDate(milestone.getDueDate());
        dto.setAmount(milestone.getAmount());

        return dto;
    }
}
