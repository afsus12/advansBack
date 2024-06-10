package com.example.finalbackendadvans.repositories;

import com.example.finalbackendadvans.entities.AppUser;
import com.example.finalbackendadvans.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface AppUserRepository  extends JpaRepository<AppUser,Long> {
     Optional<AppUser> findByUsername(String username);


     Boolean  existsByUsername(String username);
     Boolean  existsByEmail(String email);

}
