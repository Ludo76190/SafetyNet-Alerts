package com.ludo.safetynetalerts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HouseholdDto {

    private String address;
    private List<FloodDto> household;

}
