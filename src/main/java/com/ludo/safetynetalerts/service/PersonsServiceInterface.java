package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dto.ChildAlertDto;
import com.ludo.safetynetalerts.dto.PersonInfoDto;
import com.ludo.safetynetalerts.model.Persons;

import java.util.List;

public interface PersonsServiceInterface {

    List<Persons> findAll();

    List<Persons> save(Persons person);

    Persons updatePerson(Persons person);

    boolean deletePeson(String firstName, String lastName);

    Persons findByName(String firstName, String lastName);

    List<ChildAlertDto> findChild(String address);

    List<PersonInfoDto> personInfo(String firstName, String lastName);

    List<String> communityEmail(String city);
}
