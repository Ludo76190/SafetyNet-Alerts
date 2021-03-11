package com.ludo.safetynetalerts.dao;

import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonsDaoImpl implements PersonsDaoInterface {

    @Autowired
    DataBase dataBase;

    @Override
    public List<Persons> getAll() {
        return dataBase.getPersons();
    }

}
