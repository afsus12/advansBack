package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.Message;
import com.example.finalbackendadvans.entities.RepaymentMilestone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentMilstoneRepository extends JpaRepository<RepaymentMilestone, Long> {
    Page<RepaymentMilestone> findByLoanIdOrderByPaidAscDueDateAsc(Long loanId, Pageable pageable);

}
