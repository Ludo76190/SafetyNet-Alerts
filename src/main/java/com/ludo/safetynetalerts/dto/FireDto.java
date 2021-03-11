package com.ludo.safetynetalerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FireDto {

    private final String stationNumber;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final int age;
    private final String[] medications;
    private final String[] allergies;

}
