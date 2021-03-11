package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dao.PersonsDaoInterface;
import com.ludo.safetynetalerts.dto.ChildAlertDto;
import com.ludo.safetynetalerts.dto.PersonInfoDto;
import com.ludo.safetynetalerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonsServiceImpl implements PersonsServiceInterface {

    @Autowired
    PersonsDaoInterface personsDaoInterface;

    @Override
    public List<Persons> findAll() {
        return personsDaoInterface.getAll();
    }

    @Override
    public List<Persons> save(Persons person) {
        List<Persons> savePerson = personsDaoInterface.getAll();
        for (Persons exitPerson : savePerson) {
            if (exitPerson.getFirstName().equals(person.getFirstName()) && exitPerson.getLastName().equals(person.getLastName())) {
                return null;
            }
        }
        savePerson.add(person);
        return savePerson;
    }

    @Override
    public Persons updatePerson(Persons person) {
        for (Persons majPerson : personsDaoInterface.getAll()) {
            if (majPerson.getFirstName().equals(person.getFirstName()) && majPerson.getLastName().equals(person.getLastName())) {
                majPerson.setAddress((person.getAddress()));
                majPerson.setCity(person.getCity());
                majPerson.setZip(person.getZip());
                majPerson.setPhone((person.getPhone()));
                majPerson.setEmail(person.getEmail());
                return majPerson;
            }
        }
        return null;
    }

    @Override
    public boolean deletePeson(String firstName, String lastName) {
        List<Persons> deletePerson = personsDaoInterface.getAll();
        return deletePerson.removeIf(persons -> persons.getFirstName().equals(firstName) && persons.getLastName().equals(lastName));
    }

    @Override
    public Persons findByName(String firstName, String lastName) {
        for (Persons person : personsDaoInterface.getAll()) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public List<ChildAlertDto> findChild(String address) {

        List<ChildAlertDto> findChilAlert = new ArrayList<>();
        boolean isWhithChild = false;

        for (Persons person : personsDaoInterface.getAll()) {
            if (person.getAddress().equals(address)) {
                System.out.println(person.getMedicalRecords().getAge());
                if (person.getMedicalRecords().getAge() <= 18) {
                    isWhithChild = true;
                }
                findChilAlert.add(new ChildAlertDto((person.getFirstName()), person.getLastName(), person.getMedicalRecords().getAge()));
            }
        }

        if (isWhithChild) {
            return findChilAlert;
        }
        return null;
    }

    @Override
    public List<PersonInfoDto> personInfo(String firstName, String lastName) {

        List<PersonInfoDto> personsInfosList = new ArrayList<>();
        boolean personExist = false;

        for (Persons person : personsDaoInterface.getAll()) {
            if (person.getLastName().equals(lastName)) {
                PersonInfoDto personInfos = new PersonInfoDto(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getCity(),
                        person.getZip(),
                        person.getMedicalRecords().getAge(),
                        person.getEmail(),
                        person.getMedicalRecords().getMedications(),
                        person.getMedicalRecords().getAllergies());
                personsInfosList.add(personInfos);
                personExist = true;
            }
        }
        if (personExist) {
            return personsInfosList;
        }
        return null;
    }

    @Override
    public List<String> communityEmail(String city) {

        List<String> personsEmail = new ArrayList<>();

        for (Persons person : personsDaoInterface.getAll()) {
            if (person.getCity().equals(city)) {
                personsEmail.add(person.getEmail());
            }
        }

        return personsEmail;
    }
}
