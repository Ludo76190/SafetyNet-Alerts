package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dto.FireDto;
import com.ludo.safetynetalerts.dto.HouseholdDto;
import com.ludo.safetynetalerts.dto.PersonCounterDto;
import com.ludo.safetynetalerts.model.Firestations;

import java.util.List;

/**
 * FireStation service interface.
 *
 * @author Ludovic Allegaert
 */

public interface FirestationsServiceInterface {

    List<Firestations> findAll();

    List<Firestations> save(Firestations firestation);

    Firestations updateFirestation(Firestations firestation);

    boolean deleteFirestation(String address);

    PersonCounterDto findByNumber(String number);

    List<String> phoneAlert(String firestation);

    List<FireDto> fire(String address);

    List<HouseholdDto> flood(List<String> listStations);

}
