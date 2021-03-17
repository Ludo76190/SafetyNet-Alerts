package com.ludo.safetynetalerts.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ludo.safetynetalerts.service.AgeCalculator;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Data
@Component
public class DataBase {

    private List<Persons> persons;
    private List<Firestations> firestations;
    private List<MedicalRecords> medicalrecords;

    private static final Logger logger = LogManager.getLogger(DataBase.class);

    @PostConstruct
    public void init() {
        DataBase dataBase = new DataBase();
        byte[] jsonData = new byte[0];;

        try {
            //read json file data to String
            logger.debug("Loading Data File");
            jsonData = Files.readAllBytes(Paths.get("src/main/resources/data.json"));
            logger.debug("Data File loaded");
        } catch (IOException e) {
            logger.debug("Error while loading Data File");
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            dataBase = mapper.readValue(jsonData, DataBase.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.persons = dataBase.getPersons();
        this.firestations = dataBase.getFirestations();
        this.medicalrecords = dataBase.getMedicalrecords();

        for (Persons person: this.persons ) {
            for (MedicalRecords medicalRecords : this.medicalrecords) {
                if (medicalRecords.getFirstName().equals(person.getFirstName()) && medicalRecords.getLastName().equals(person.getLastName())) {
                    person.setMedicalRecords(medicalRecords);
                }
            }
        }

        for (Persons person: this.persons) {
            for (Firestations firestation : this.firestations) {
                if (firestation.getAddress().equals(person.getAddress())) {
                    person.setFirestations(firestation);
                    //firestation.getPersons().add(person);
                    firestation.setPersons(persons);
                }
            }
        }

        for (MedicalRecords medicalRecord : dataBase.medicalrecords) {
            int age = AgeCalculator.calculateAge(medicalRecord.getBirthdate(), LocalDate.now());
            medicalRecord.setAge(age);
        }

    }
}