package com.ludo.safetynetalerts.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        DataBase dataBase;
        try {
            //read json file data to String
            logger.debug("Loading Data File");
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/data.json"));

            ObjectMapper mapper = new ObjectMapper();
            dataBase = mapper.readValue(jsonData, DataBase.class);
            this.persons = dataBase.getPersons();
            this.firestations = dataBase.getFirestations();
            this.medicalrecords = dataBase.getMedicalrecords();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
