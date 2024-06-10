package com.example.finalbackendadvans.entities.StaffEntities;

import com.example.finalbackendadvans.entities.Staff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prospect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userCode;

    private String customer;
    private String cin;
    private String firstname;

    private String lastname;

    private String email;

    private String bornon;

    private String title;

    private String branch;

    private String gender;

    private String fieldOfActivity;

    private String jobOfTheHolder;

    private String subJobOfTheHolder;

    private String nbrSalariesFamiliaux;

    private String typePosition;

    private String phoneNumber;

    private String address;
    @OneToOne
    private Opportunity opportunity;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonBackReference
    private Staff staff;


}
