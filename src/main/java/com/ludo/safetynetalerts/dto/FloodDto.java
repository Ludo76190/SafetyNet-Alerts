package com.ludo.safetynetalerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FloodDto {

    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private String[] medications;
    private String[] allergies;

}
