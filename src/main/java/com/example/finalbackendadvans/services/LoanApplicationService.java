package com.example.finalbackendadvans.services;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Client.LoanApplication;

import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private ClientRepository  clientRepository;
    public boolean hasActiveLoanApplications(Long clientId) {
        // Assuming you have a way to fetch the Client entity by clientId
        Client client = clientRepository.getClientById(clientId);

        List<LoanApplication> loanApplications = loanApplicationRepository.findByClient(client);

        if (loanApplications.isEmpty()) {
            return true;
        }

        for (LoanApplication loanApplication : loanApplications) {
            System.out.println("Status ==="+loanApplication.getStatus().toString());
            if (loanApplication.getStatus() != LoanApplication.Status.REFUSED &&
                    loanApplication.getStatus() != LoanApplication.Status.COMPLETED) {


                return false;
            }
        }

        return true;
    }

    // Method to fetch Client entity by clientId

}