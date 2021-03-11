package com.ludo.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Persons {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    @JsonIgnore
    private MedicalRecords medicalRecords;

    @JsonIgnore
    private Firestations firestations;


}
