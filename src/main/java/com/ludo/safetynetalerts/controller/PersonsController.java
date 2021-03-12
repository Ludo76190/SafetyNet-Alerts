package com.ludo.safetynetalerts.controller;

import com.ludo.safetynetalerts.dto.ChildAlertDto;
import com.ludo.safetynetalerts.dto.PersonInfoDto;
import com.ludo.safetynetalerts.model.Persons;
import com.ludo.safetynetalerts.service.PersonsServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PersonsController {

    private static final Logger logger = LogManager.getLogger(FirestationsController.class);

    @Autowired
    private PersonsServiceInterface personsServiceInterface;

    //Persons
    @GetMapping("/persons")
    public List<Persons> findAll(HttpServletResponse response) {

        List<Persons> findAll = personsServiceInterface.findAll();

        if (findAll != null) {
            logger.info("SUCCESS - findAll persons GET request");
            response.setStatus(200);
        } else {
            logger.error("FAILED - findAll persons GET request");
            response.setStatus(404);
        }

        return findAll;

    }

    @GetMapping("/persons/info")
    public Persons findByName(@RequestParam String firstName, @RequestParam String lastName, HttpServletResponse response) {

        if(personsServiceInterface.findByName(firstName, lastName) != null){
            logger.info("SUCCESS - infos persons GET request");
            response.setStatus(200);
            return personsServiceInterface.findByName(firstName, lastName);
        } else {
            logger.error("FAILED - infos persons GET request");
            response.setStatus(404);
        }
        return null;

    }

    @GetMapping("/childAlert")
    public List<ChildAlertDto> listChild(@RequestParam String address, HttpServletResponse response) {

        List<ChildAlertDto> listChild = personsServiceInterface.findChild(address);

        if (listChild != null) {
            logger.info("SUCCESS - listChild GET request - address = "+address);
            response.setStatus(200);
            return listChild;
        } else {
            logger.error("FAILED - listChild GET request - address = "+address);
            response.setStatus(404);
        }
        return null;

    }

    @GetMapping("personInfo")
    public List<PersonInfoDto> personInfo(@RequestParam String firstName, @RequestParam String lastName, HttpServletResponse response) {

        List<PersonInfoDto> personInfo = personsServiceInterface.personInfo(firstName, lastName);

        if (personInfo != null) {
            logger.info("SUCCESS - personInfo GET request - Person = " + firstName + " " + lastName);
            response.setStatus(200);
            return personInfo;
        } else {
            logger.error("FAILED - personInfo GET request - Person = " + firstName + " " + lastName);
            response.setStatus(404);
        }
        return null;

    }

    @GetMapping("communityEmail")
    public List<String> communityEmail(@RequestParam String city, HttpServletResponse response) {

        List<String> communityEmail = personsServiceInterface.communityEmail(city);

        if (communityEmail != null) {
            logger.info("SUCCESS - communityEmail GET request - city = " + city);
            response.setStatus(200);
            return communityEmail;
        } else {
            logger.error("FAILED - communityEmail GET request - city = " + city);
            response.setStatus(404);
        }
        return null;

    }

    @PostMapping("/persons")
    //@ResponseStatus(HttpStatus.CREATED)
    public List<Persons> addPerson(@RequestBody Persons person, HttpServletResponse response) {

        List<Persons> newPerson = personsServiceInterface.save(person);

        if (newPerson != null){
            logger.info("SUCCESS - addPerson POST request - Person " + person.getFirstName() + " " + person.getLastName());
            response.setStatus(201);
            return newPerson;
        } else {
            logger.error("FAILED - addPerson POST request - person " + person.getFirstName() + " " + person.getLastName());
            response.setStatus(409);
        }
        return null;

    }

    @PutMapping("/persons")
    public Persons updatePerson(@RequestBody Persons person, HttpServletResponse response) {

        Persons majPerson = personsServiceInterface.updatePerson(person);

        if (majPerson != null) {
            logger.info("SUCCESS - majPerson PUT request - Person " + person.getFirstName() + " " + person.getLastName());
            response.setStatus(200);
            return majPerson;
        } else {
            logger.error("FAILED - majPerson PUT request - Person " + person.getFirstName() + " " + person.getLastName());
            response.setStatus(404);
        }
        return null;

     }


    @DeleteMapping("/persons")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName, HttpServletResponse response) {

        if (personsServiceInterface.deletePeson(firstName, lastName)) {
            logger.info("SUCCESS - deletePerson - Person " + firstName + " " + lastName);
            response.setStatus(200);
        } else {
            logger.error("FAILED - deletePerson - Person " + firstName + " " + lastName);
            response.setStatus(404);
        }
    }

}
