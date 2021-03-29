package com.ludo.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Firestations {
    private String address;
    private String station;

    @JsonIgnore
    private List<com.ludo.safetynetalerts.model.Persons> persons = new ArrayList<>();

}
