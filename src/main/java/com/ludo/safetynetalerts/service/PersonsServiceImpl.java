package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dao.PersonsDaoInterface;
import com.ludo.safetynetalerts.dto.ChildAlertDto;
import com.ludo.safetynetalerts.dto.PersonInfoDto;
import com.ludo.safetynetalerts.model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonsServiceImpl implements PersonsServiceInterface {

    /**
     * Logger class.
     */
    private static final Logger logger = LogManager.getLogger(AgeCalculator.class);

    @Autowired
    PersonsDaoInterface personsDaoInterface;


    /**
     * Retourne l'ensemble des personnes existantes
     *
     * @return Liste des personnes
     */
    @Override
    public List<Persons> findAll() {
        try {
            return personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la récupération des personnes :" + exception.getMessage());
        }
        return null;
    }

    /**
     * Sauvegarde la liste des personnes passées en paramètre
     *
     * @param person personne à sauvegarder
     */
    @Override
    public List<Persons> save(Persons person) {
        try {
            List<Persons> savePerson = personsDaoInterface.getAll();
            for (Persons exitPerson : savePerson) {
                if (exitPerson.getFirstName().equals(person.getFirstName()) && exitPerson.getLastName().equals(person.getLastName())) {
                    return null;
                }
            }
            savePerson.add(person);
            return savePerson;
        } catch (Exception exception) {
            logger.error("Erreur lors de l'enregistrement de la personne " + exception.getMessage());
        }
        return null;
    }

    /**
     * Met à jour d'une personne
     *
     * @param person Personne à mettre à jour
     * @return Personne mise à jour, null si la mise à jour a échoué ou que la personne n'existait pas
     */
    @Override
    public Persons updatePerson(Persons person) {
        if (person != null) {
            List<Persons> persons;
            try {
                persons = personsDaoInterface.getAll();
            } catch (Exception exception){
                logger.error("Erreur lors de la mise à jour d'une personne : " + exception.getMessage());
                return null;
            }
            for (Persons majPerson : persons) {
                if (majPerson.getFirstName().equals(person.getFirstName()) && majPerson.getLastName().equals(person.getLastName())) {
                    majPerson.setAddress((person.getAddress()));
                    majPerson.setCity(person.getCity());
                    majPerson.setZip(person.getZip());
                    majPerson.setPhone((person.getPhone()));
                    majPerson.setEmail(person.getEmail());
                    return majPerson;
                }
            }
        }
        return null;
    }

    /**
     * Suppression d'une personne
     *
     * @param firstName prénom de la personne à supprimer
     * @param lastName  nom de la personne à supprimer
     */
    @Override
    public boolean deletePeson(String firstName, String lastName) {
        try {
            List<Persons> deletePerson = personsDaoInterface.getAll();
            return deletePerson.removeIf(persons -> persons.getFirstName().equals(firstName) && persons.getLastName().equals(lastName));
        } catch (Exception exception) {
            logger.error("Erreur lors de la suppression de la personne "+ firstName + " " + lastName + " " + exception.getMessage());
        }
        return false;
    }

    /**
     * Recherche d'une personne par le prénom et le nom
     *
     * @param firstName prénom de la personne à rechercher
     * @param lastName  nom de la personne à rechercher
     */
    @Override
    public Persons findByName(String firstName, String lastName) {
        try {
            for (Persons person : personsDaoInterface.getAll()) {
                if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                    return person;
                }
            }
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche findByName pour la personne " + firstName + " " + lastName + " " + exception.getMessage());
        }
        return null;
    }

    /**
     * Recherche des foyer avec un enfant
     *
     * @param address adresse des foyers concernés
     */
    @Override
    public List<ChildAlertDto> findChild(String address) {

        List<ChildAlertDto> findChilAlert = new ArrayList<>();
        List<Persons> persons = new ArrayList<>();
        boolean isWhithChild = false;

        try {
            persons = personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche findChild pour l'adresse " + address + " " + exception.getMessage());
        }

        for (Persons person : persons) {
            if (person.getAddress().equals(address)) {
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

    /**
     * Recherche d'une personne par le prénom et le nom
     *
     * @param firstName prénom de la personne à rechercher
     * @param lastName  nom de la personne à rechercher
     */
    @Override
    public List<PersonInfoDto> personInfo(String firstName, String lastName) {

        List<PersonInfoDto> personsInfosList = new ArrayList<>();
        List<Persons> persons = new ArrayList<>();
        boolean personExist = false;

        try {
            persons = personsDaoInterface.getAll();
        } catch (Exception exception) {
            logger.error("Erreur lors de la recherche personInfo pour la personne " + firstName + " " + lastName + " " + exception.getMessage());
        }

        for (Persons person : persons) {
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

        try {
            for (Persons person : personsDaoInterface.getAll()) {
                if (person.getCity().equals(city)) {
                    personsEmail.add(person.getEmail());
                }
            }

            return personsEmail;
        } catch (Exception exception) {
            logger.error("Erreur lor de la recherche des emails par ville " + exception.getMessage());
        }
        return null;
    }
}
