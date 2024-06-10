package com.example.finalbackendadvans.services;


import com.example.finalbackendadvans.entities.StaffEntities.Opportunity;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.repositories.OpportunityRepository;
import com.example.finalbackendadvans.repositories.ProspectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OpportunityService {

    private  final ProspectRepository prospectRepository;
    private  final OpportunityRepository opportunityRepository;
    public  OpportunityService(ProspectRepository prospectRepository,OpportunityRepository opportunityRepository){

        this.prospectRepository=prospectRepository;
        this.opportunityRepository=opportunityRepository;

    }

    public Prospect addOpportunity(Opportunity opportunity) {
        try {
            if(!prospectRepository.existsByCustomer(opportunity.getCustomer())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
            Prospect prosp = prospectRepository.findProspectByCustomer(opportunity.getCustomer());

            prosp.setOpportunity(opportunityRepository.save(opportunity));
            return prospectRepository.save(prosp);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password", e);

        }


    }
}
