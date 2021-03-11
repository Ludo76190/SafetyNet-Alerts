package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dao.FirestationsDaoInterface;
import com.ludo.safetynetalerts.dao.PersonsDaoInterface;
import com.ludo.safetynetalerts.dto.FireDto;
import com.ludo.safetynetalerts.dto.FloodDto;
import com.ludo.safetynetalerts.dto.HouseholdDto;
import com.ludo.safetynetalerts.dto.PersonCounterDto;
import com.ludo.safetynetalerts.model.Firestations;
import com.ludo.safetynetalerts.model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationsServiceImpl implements FirestationsServiceInterface{

    private static final Logger logger = LogManager.getLogger(FirestationsServiceImpl.class);

    @Autowired
    FirestationsDaoInterface firestationsDaoInterface;

    @Autowired
    PersonsDaoInterface personsDaoInterface;

    @Override
    public List<Firestations> findAll() {
        return firestationsDaoInterface.getAll();
    }

    @Override
    public List<Firestations> save(Firestations savedFirestation) {
        List<Firestations> firestation = firestationsDaoInterface.getAll();
        for (Firestations firestations : firestation) {
            if (firestations.getAddress().equals(savedFirestation.getAddress())) {
                return null;
            }
        }
        firestation.add(savedFirestation);
        return firestation;
    }

    @Override
    public Firestations updateFirestation(Firestations updatedFirestation) {
        for (Firestations firestation : firestationsDaoInterface.getAll()) {
            if (firestation.getAddress().equals(updatedFirestation.getAddress()) && firestation.getStation().equals(updatedFirestation.getStation())) {
                logger.error("Firestation already Exist.");
            } else if (firestation.getAddress().equals(updatedFirestation.getAddress()) && !firestation.getStation().equals(updatedFirestation.getStation())) {
                firestation.setStation(updatedFirestation.getStation());
                return firestation;
            }
        }
        return null;
    }

    @Override
    public boolean deleteFirestation(String address) {
        List<Firestations> firestations = firestationsDaoInterface.getAll();
        return firestations.removeIf(firestation -> firestation.getAddress().equals(address));
    }

    @Override
    public PersonCounterDto findByNumber(String number) {

        List<String> personAddress = new ArrayList<>();
        List<Persons> foundPerson = new ArrayList<>();

        int nbChildren =0;
        int nbAdults = 0;

        for (Firestations firestation : firestationsDaoInterface.getAll()) {
            if (firestation.getStation().equals(number)) {
                personAddress.add(firestation.getAddress());
            }
        }

        for (Persons person : personsDaoInterface.getAll()) {
            if (personAddress.contains(person.getAddress())) {
                if (person.getMedicalRecords().getAge() > 18) {
                    nbAdults++;
                } else {
                    nbChildren++;
                }
                foundPerson.add(person);
            }
        }

        return new PersonCounterDto(foundPerson, nbAdults, nbChildren);
    }

    @Override
    public List<String> phoneAlert(String number) {

        List<String> personAddress = new ArrayList<>();
        List<String> personPhone = new ArrayList<>();
        boolean stationNumberExist = false;

        for (Firestations firestation : firestationsDaoInterface.getAll()) {
            if (firestation.getStation().equals(number)) {
                personAddress.add(firestation.getAddress());
                stationNumberExist = true;
            }
        }

        if (stationNumberExist) {
            for (Persons person : personsDaoInterface.getAll()) {
                if (personAddress.contains(person.getAddress())) {
                    personPhone.add(person.getPhone());
                }
            }

            return personPhone.stream().distinct().collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<FireDto> fire(String address) {

        boolean addressExist = false;
        List<FireDto> fireDtoList = new ArrayList<>();

        for (Firestations firestation : firestationsDaoInterface.getAll()) {
            if (firestation.getAddress().equals(address)) {
                addressExist = true;
            }
        }

        if (addressExist) {
            for (Persons person : personsDaoInterface.getAll()) {
                if (person.getFirestations().getAddress().equals(address)) {
                    FireDto fireDtoPerson = new FireDto(person.getFirestations().getStation(), person.getFirstName(), person.getLastName(), person.getPhone(), person.getMedicalRecords().getAge(), person.getMedicalRecords().getMedications(), person.getMedicalRecords().getAllergies());
                    fireDtoList.add(fireDtoPerson);
                }
            }
            return fireDtoList;
        }

        return null;
    }

    @Override
    public List<HouseholdDto> flood(List<String> listStations) {

        List<String> foundAddress = new ArrayList<>();
        List<HouseholdDto> householdsList = new ArrayList<>();
        boolean stationExist = false;

        for (String station : listStations) {
            for (Firestations firestation : firestationsDaoInterface.getAll()) {
                if (firestation.getStation().equals(station)) {
                    foundAddress.add(firestation.getAddress());
                    stationExist = true;
                } else {
                    continue;
                }
            }

            if (stationExist) {
                for (String address : foundAddress) {
                    List<FloodDto> floodList = new ArrayList<>();
                    for (Persons person : personsDaoInterface.getAll()) {
                        if (person.getAddress().equals(address)) {
                            FloodDto flood = new FloodDto(person.getFirstName(), person.getLastName(), person.getPhone(), person.getMedicalRecords().getAge(), person.getMedicalRecords().getMedications(), person.getMedicalRecords().getAllergies());
                            floodList.add(flood);
                        }
                    }
                    householdsList.add(new HouseholdDto(address, floodList));
                }
            }

            LinkedHashSet<HouseholdDto> hashSet = new LinkedHashSet<>(householdsList);

            return new ArrayList<>(hashSet);
        }

        return null;

    }
}
