package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsServiceInterface {

    List<MedicalRecords> findAll();

    List<MedicalRecords> save(MedicalRecords medicalRecord);

    MedicalRecords updateMedicalRecord(MedicalRecords medicalRecord);

    boolean deleteMedicalRecord(String firstName, String lastName);
}
