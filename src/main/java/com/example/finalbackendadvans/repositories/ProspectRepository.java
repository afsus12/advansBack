package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.StaffEntities.Opportunity;
import com.example.finalbackendadvans.entities.StaffEntities.Prospect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProspectRepository extends JpaRepository<Prospect,Long> {
    List<Prospect> findAllByStaffId(Long id);
    List<Prospect> findAllByUserCode(long userCode);
    Prospect findProspectByCustomer(String customer);
    Boolean existsByCustomer(String customer);
    Prospect findIndividualByOpportunity(Opportunity opportunity);
    Page<Prospect> findByUserCodeAndFirstnameContainingAndLastnameContainingAndCustomerContaining(long userCode,
            String firstname, String lastname, String customer, Pageable pageable);
}
