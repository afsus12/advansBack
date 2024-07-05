package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByClientId(Long clientId);
    List<Message> findByStaffId(Long staffId);}
