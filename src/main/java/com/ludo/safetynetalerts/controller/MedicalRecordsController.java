package com.ludo.safetynetalerts.controller;

import com.ludo.safetynetalerts.model.MedicalRecords;
import com.ludo.safetynetalerts.service.MedicalRecordsServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class MedicalRecordsController {

    private static final Logger logger = LogManager.getLogger(com.ludo.safetynetalerts.controller.FirestationsController.class);

    @Autowired
    private MedicalRecordsServiceInterface medicalRecordsServiceInterface;

    //MedicalRecords
    @GetMapping("/medicalRecords")
    public List<MedicalRecords> findAll() {

        List<MedicalRecords> findAll = medicalRecordsServiceInterface.findAll();

        if (findAll != null) {
            logger.info("SUCCESS - findAll medicalrecords GET request");
        } else {
            logger.error("FAILED - findAll medicalrecords GET request");
        }

        return findAll;
    }

    @PostMapping("/medicalRecords")
    //@ResponseStatus(HttpStatus.CREATED)
    public List<MedicalRecords> addMedicalRecords(@RequestBody MedicalRecords medicalRecord, HttpServletResponse response) {

        List<MedicalRecords> newMedicalRecords = medicalRecordsServiceInterface.save(medicalRecord);

        if (newMedicalRecords != null) {
            logger.info("SUCCESS - addMedicalRecords POST request - medicalRecord : " + medicalRecord );
            response.setStatus(201);
        } else {
            logger.error("FAILED - addMedicalRecords POST request - medicalRecord : " + medicalRecord );
            response.setStatus(409);
        }

        return newMedicalRecords;
    }

    @PutMapping("/medicalRecords")
    public MedicalRecords updateMedicalRecords(@RequestBody MedicalRecords medicalRecord) {

        MedicalRecords updateMedicalRecords =  medicalRecordsServiceInterface.updateMedicalRecord(medicalRecord);

        if (updateMedicalRecords != null) {
            logger.info("SUCCESS - updateMedicalRecords PUT request - Firestation :" + medicalRecord);
        } else {
            logger.error("FAILED - updateMedicalRecords PUT request - Firestation : " + medicalRecord);
        }

        return updateMedicalRecords;
    }

    @DeleteMapping("/medicalRecords")
    public void deleteMedicalRecords(@RequestParam String firstName, @RequestParam String lastName) {

        if (medicalRecordsServiceInterface.deleteMedicalRecord(firstName, lastName)) {
            logger.info("SUCCESS - deleteMedicalRecord - MedicalRecord for person  :" + firstName + " " + lastName);
        } else {
            logger.error("FAILED - deleteMedicalRecord - MedicalRecord for person  :" + firstName + " " + lastName);
        }
    }
}
