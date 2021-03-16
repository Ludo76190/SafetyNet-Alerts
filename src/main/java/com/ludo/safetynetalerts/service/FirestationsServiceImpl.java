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

    /**
     * Logger class.
     */
    private static final Logger logger = LogManager.getLogger(FirestationsServiceImpl.class);

    @Autowired
    FirestationsDaoInterface firestationsDaoInterface;

    @Autowired
    PersonsDaoInterface personsDaoInterface;

    /**
     * Récupère la liste de toutes les casernes
     *
     * @return Liste des casernes existantes
     */
    @Override
    public List<Firestations> findAll() {
        try {
            return firestationsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des casernes: " + exception.getMessage());
        }
        return null;
    }

    /**
     * Sauvegarder la liste des casernes
     *
     * @param savedFirestation Liste à sauvegarder
     */
    @Override
    public List<Firestations> save(Firestations savedFirestation) {
        try {
            List<Firestations> firestation = firestationsDaoInterface.getAll();
            for (Firestations firestations : firestation) {
                if (firestations.getAddress().equals(savedFirestation.getAddress())) {
                    return null;
                }
            }
            firestation.add(savedFirestation);
            return firestation;
        } catch (Exception exception) {
            logger.error("Erreur lors de la sauvegarde des casernes: " + exception.getMessage());
        }
        return null;
    }

    /**
     * Mise à jour du numéro d'une caserne
     *
     * @param updatedFirestation caserne à mettre à jour
     */
    @Override
    public Firestations updateFirestation(Firestations updatedFirestation) {
        try {
            for (Firestations firestation : firestationsDaoInterface.getAll()) {
                if (firestation.getAddress().equals(updatedFirestation.getAddress()) && firestation.getStation().equals(updatedFirestation.getStation())) {
                    logger.error("Firestation already Exist.");
                } else if (firestation.getAddress().equals(updatedFirestation.getAddress()) && !firestation.getStation().equals(updatedFirestation.getStation())) {
                    firestation.setStation(updatedFirestation.getStation());
                    return firestation;
                }
            }
        } catch (Exception exception) {
            logger.error("Erreur lors de la mise à jour de la caserne ;" + exception.getMessage());
        }
        return null;
    }

    /**
     * suppression d'une d'une caserne avec son adresse
     *
     * @param address adresse de la caserne à supprimer
     */
    @Override
    public boolean deleteFirestation(String address) {
        try {
            List<Firestations> firestations = firestationsDaoInterface.getAll();
            return firestations.removeIf(firestation -> firestation.getAddress().equals(address));
        } catch (Exception exception) {
            logger.error("Erreur lors de la suppression de la caserne : " + exception.getMessage());
        }
        return false;
    }

    /**
     * recherche d'une d'une caserne avec son numéro
     *
     * @param number numéro de la caserne à rechercher
     */
    @Override
    public PersonCounterDto findByNumber(String number) {

        List<Persons> foundPerson = new ArrayList<>();

        List<Persons> persons = new ArrayList<>();
        try {
            persons=personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche des caserne par le numéro: " + exception.getMessage());
            return null;
        }

        int nbChildren =0;
        int nbAdults = 0;

        for (Persons person : persons) {
            if (person.getFirestations().getStation().equals(number)) {
                if (person.getMedicalRecords().getAge() > 18) {
                    nbAdults++;
                } else {
                    nbChildren++;
                }
                foundPerson.add(person);
            }
        }

        if (foundPerson.isEmpty()) {
            return null;
        }
        return new PersonCounterDto(foundPerson, nbAdults, nbChildren);
    }

    /**
     * recherche des numéro de téléphone assoicé au numéro de caserne
     *
     * @param number numéro de la caserne à rechercher
     */
    @Override
    public List<String> phoneAlert(String number) {

        List<String> personPhone = new ArrayList<>();

        List<Persons> persons = new ArrayList<>();
        try {
            persons=personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche des numéro de téléphone associé au numéro de la caserne: " + exception.getMessage());
        }

        for (Persons person : persons) {
            if (person.getFirestations().getStation().equals(number)) {
                personPhone.add(person.getPhone());
            }
        }

        if (personPhone.isEmpty()) {
            return null;
        }

        return personPhone.stream().distinct().collect(Collectors.toList());
    }

    /**
     * recherche des personnes par adresse
     *
     * @param address des habitants à rechercher
     */
    @Override
    public List<FireDto> fire(String address) {

        List<FireDto> fireDtoList = new ArrayList<>();

        List<Persons> persons = new ArrayList<>();
        try {
            persons=personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche des numéro de téléphone associé au numéro de la caserne: " + exception.getMessage());
        }

        for (Persons person : persons) {
            if (person.getFirestations().getAddress().equals(address)) {
                FireDto fireDtoPerson = new FireDto(person.getFirestations().getStation(), person.getFirstName(), person.getLastName(), person.getPhone(), person.getMedicalRecords().getAge(), person.getMedicalRecords().getMedications(), person.getMedicalRecords().getAllergies());
                fireDtoList.add(fireDtoPerson);
            }
        }

        if (fireDtoList.isEmpty()) {
            return null;
        }

        return fireDtoList;
    }

    /**
     * recherche des foyer déservis par une caserne
     *
     * @param listStations liste des casernes par leurs numéros
     */
    @Override
    public List<HouseholdDto> flood(List<String> listStations) {

        List<String> foundAddress = new ArrayList<>();
        List<HouseholdDto> householdsList = new ArrayList<>();
        boolean stationExist = false;

        List<Persons> persons = new ArrayList<>();
        try {
            persons = personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche des numéro de téléphone associé au numéro de la caserne: " + exception.getMessage());
            return null;
        }

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
                    for (Persons person : persons) {
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
