package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dao.MedicalRecordsDaoInterface;
import com.ludo.safetynetalerts.dao.PersonsDaoInterface;
import com.ludo.safetynetalerts.model.MedicalRecords;
import com.ludo.safetynetalerts.model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsServiceInterface{

    /**
     * Logger class.
     */
    private static final Logger logger = LogManager.getLogger(FirestationsServiceImpl.class);

    @Autowired
    MedicalRecordsDaoInterface medicalRecordsDaoInterface;

    @Autowired
    PersonsDaoInterface personsDaoInterface;

    /**
     * récupère la liste de toutes les données médicales
     * @return liste des données médicales
     */
    @Override
    public List<MedicalRecords> findAll() {
        try {
            return medicalRecordsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération de la liste des données médicales : " + exception.getMessage());
        }
        return null;
    }

    /**
     * Sauvergarde la liste des données médicales
     *
     * @param savedMedicalRecord liste des données médicales à sauvegarder
     */
    @Override
    public List<MedicalRecords> save(MedicalRecords savedMedicalRecord) {

        List<MedicalRecords> medicalRecords = new ArrayList<>();

        List<Persons> persons = new ArrayList<>();
        try {
            persons = personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la sauvegarde des données médicales: " + exception.getMessage());
        }

        for (Persons person : persons) {
            if (person.getFirstName().equalsIgnoreCase(savedMedicalRecord.getFirstName()) && person.getLastName().equalsIgnoreCase(savedMedicalRecord.getLastName()) && person.getMedicalRecords() == null) {
                medicalRecords.add(savedMedicalRecord);
                return medicalRecords;
            }
        }

        return null;

    }

    /**
     * mise à jour d'une donnée médicale
     * @param updatedMedicalRecord donnée médicale à mettre à jour
     * @return donnée médicale mise à jour
     */
    @Override
    public MedicalRecords updateMedicalRecord(MedicalRecords updatedMedicalRecord) {

        List<MedicalRecords> medicalRecords = new ArrayList<>();
        try {
            medicalRecords = medicalRecordsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la mise à jour d'une donnée médicale : " + exception.getMessage());
            return null;
        }
        for (MedicalRecords medicalRecord : medicalRecords) {
            if (medicalRecord.getFirstName().equals(updatedMedicalRecord.getFirstName()) && medicalRecord.getLastName().equals(updatedMedicalRecord.getLastName())) {
                medicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
                medicalRecord.setMedications(updatedMedicalRecord.getMedications());
                medicalRecord.setAllergies((updatedMedicalRecord.getAllergies()));
                return medicalRecord;
            }
        }
        return null;
    }

    /**
     * suppression d'une donnée médicale par le prénom et nom de la personne
     * @param firstName prénom
     * @param lastName nom
     * @return true si la suppression est faite, sinon false
     */
    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {

        List<MedicalRecords> medicalRecords = new ArrayList<>();

        try {
            medicalRecords = medicalRecordsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la suppression d'une donnée médicale :" + exception.getMessage());
        }
        return medicalRecords.removeIf(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
    }
}
