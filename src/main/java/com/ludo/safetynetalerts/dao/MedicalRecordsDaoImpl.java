package com.ludo.safetynetalerts.dao;

import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.MedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordsDaoImpl implements MedicalRecordsDaoInterface {

    @Autowired
    DataBase dataBase;

    @Override
    public List<MedicalRecords> getAll() {
        return dataBase.getMedicalrecords();
    }
}
