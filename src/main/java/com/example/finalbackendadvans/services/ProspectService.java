package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.entities.Staff;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import com.example.finalbackendadvans.repositories.AppUserRepository;
import com.example.finalbackendadvans.repositories.ProspectRepository;
import com.example.finalbackendadvans.repositories.StaffRepository;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Service
public class ProspectService {
    private final ProspectRepository prospectRepository;
    private final sendEmailService sendEmailService;

    private final StaffRepository staffRepository;

    public Page<Prospect> getFilteredProspects(Long userCode,  String firstname, String lastname, String customer, Pageable pageable) {
        return prospectRepository.findByUserCodeAndFirstnameContainingAndLastnameContainingAndCustomerContaining(userCode,
                firstname, lastname, customer, pageable);
    }
    public  ProspectService(ProspectRepository prospectRepository ,StaffRepository staffRepository,sendEmailService sendEmailService){
        this.prospectRepository=prospectRepository;

        this.staffRepository=staffRepository;
         this.sendEmailService=sendEmailService;
    }
    public Prospect addProspect(Prospect prospect) throws MessagingException {
        Staff staff = staffRepository.findById(prospect.getUserCode()).orElseThrow(() -> new RuntimeException("Staff not found"));
        prospect.setStaff(staff);

        final int SHORT_ID_LENGTH = 8;

// all possible unicode characters
        String shortId = RandomStringUtils.randomNumeric(8);
        prospect.setCustomer(shortId);
        sendEmailService.sendEmail(prospect.getFirstname()+" "+prospect.getLastname(),prospect.getCustomer(),prospect.getEmail(),"Welcome to Advans Tunisie! Your Unique Code for Account Creation and Loan Application");
        return prospectRepository.save(prospect);
    };

    public ResponseEntity<?> getProspectsByCustomerCode(String customer) {
        Prospect prospect=prospectRepository.findProspectByCustomer(customer);
        if(prospect==null){

            return new ResponseEntity<>("not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prospect, HttpStatus.OK);
    }
    public List<Prospect> getProspectsByStaffId(Long userCode) {
        return prospectRepository.findAllByStaffId(userCode);
    }
}
