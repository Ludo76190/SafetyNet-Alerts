package com.ludo.safetynetalerts.dao;

import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.Firestations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationsDaoImpl implements FirestationsDaoInterface{

    @Autowired
    DataBase dataBase;

    @Override
    public List<Firestations> getAll() {
        return dataBase.getFirestations();
    }

}
