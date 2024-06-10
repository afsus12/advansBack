package com.example.finalbackendadvans.repositories;


import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface StaffRepository extends JpaRepository<Staff,Long> {

}
