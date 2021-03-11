package com.ludo.safetynetalerts.dto;

import com.ludo.safetynetalerts.model.Persons;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PersonCounterDto {

    private final List<Persons> personsStationList;
    private final int adultsNumber;
    private final int childrenNumber;
}
