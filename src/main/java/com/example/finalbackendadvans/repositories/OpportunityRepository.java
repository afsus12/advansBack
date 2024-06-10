package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.StaffEntities.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity,Long> {
    Boolean existsByCustomer(String customer);

    Opportunity findByCustomer(String customer);
}
