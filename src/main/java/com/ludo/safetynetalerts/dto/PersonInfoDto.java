package com.ludo.safetynetalerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonInfoDto {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private int age;
    private String email;
    private String[] medications;
    private String[] allergies;

}
