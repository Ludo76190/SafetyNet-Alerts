package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dao.MedicalRecordsDaoInterface;
import com.ludo.safetynetalerts.dao.PersonsDaoInterface;
import com.ludo.safetynetalerts.model.MedicalRecords;
import com.ludo.safetynetalerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsServiceInterface{

    @Autowired
    MedicalRecordsDaoInterface medicalRecordsDaoInterface;

    @Autowired
    PersonsDaoInterface personsDaoInterface;

    @Override
    public List<MedicalRecords> findAll() {
        return medicalRecordsDaoInterface.getAll();
    }

    @Override
    public List<MedicalRecords> save(MedicalRecords savedMedicalRecord) {

        List<MedicalRecords> medicalRecords = medicalRecordsDaoInterface.getAll();

        for (Persons person : personsDaoInterface.getAll()) {
            if (person.getFirstName().equals(savedMedicalRecord.getFirstName()) && person.getLastName().equals(savedMedicalRecord.getLastName()) && person.getMedicalRecords() == null) {
                medicalRecords.add(savedMedicalRecord);
                return medicalRecords;
            }
        }

        return null;

    }

    @Override
    public MedicalRecords updateMedicalRecord(MedicalRecords updatedMedicalRecord) {
        for (MedicalRecords medicalRecord : medicalRecordsDaoInterface.getAll()) {
            if (medicalRecord.getFirstName().equals(updatedMedicalRecord.getFirstName()) && medicalRecord.getLastName().equals(updatedMedicalRecord.getLastName())) {
                medicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
                medicalRecord.setMedications(updatedMedicalRecord.getMedications());
                medicalRecord.setAllergies((updatedMedicalRecord.getAllergies()));
                return medicalRecord;
            }
        }
        return null;
    }

    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        List<MedicalRecords> medicalRecords = medicalRecordsDaoInterface.getAll();
        return medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }
}
