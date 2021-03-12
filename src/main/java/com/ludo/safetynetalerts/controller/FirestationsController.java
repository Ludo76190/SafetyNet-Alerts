package com.ludo.safetynetalerts.controller;

import com.ludo.safetynetalerts.dto.FireDto;
import com.ludo.safetynetalerts.dto.HouseholdDto;
import com.ludo.safetynetalerts.dto.PersonCounterDto;
import com.ludo.safetynetalerts.model.Firestations;
import com.ludo.safetynetalerts.service.FirestationsServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FirestationsController {

    private static final Logger logger = LogManager.getLogger(FirestationsController.class);

    @Autowired
    private FirestationsServiceInterface firestationsServiceInterface;

    //Firestations
    @GetMapping("/firestations")
    public List<Firestations> findAll() {

        List<Firestations> findAll = firestationsServiceInterface.findAll();

        if (findAll != null) {
            logger.info("SUCCESS - findAll firestations GET request");
        } else {
            logger.error("FAILED - findAll firestations GET request");
        }

        return findAll;
    }

    @GetMapping("firestation")
    public PersonCounterDto findFirestationByNumber(@RequestParam String stationNumber) {

        PersonCounterDto findFirestationByNumber = firestationsServiceInterface.findByNumber(stationNumber);

        if (findFirestationByNumber != null){
            logger.info("SUCCESS - findFirestationByNumber request - stationNumber ="+ stationNumber);
        } else {
            logger.error("FAILED - findFirestationByNumber request - stationNumber ="+ stationNumber);
        }

        return firestationsServiceInterface.findByNumber(stationNumber);
    }

    @PostMapping("/firestations")
    //@ResponseStatus(HttpStatus.CREATED)
    public List<Firestations> addFirestation(@RequestBody Firestations firestation, HttpServletResponse response) {

        List<Firestations> addFirestation = firestationsServiceInterface.save(firestation);

        if (addFirestation != null){
            logger.info("SUCCESS - addFirestation POST request - Firestation number: " + firestation.getStation()+" - Firestation address: " + firestation.getAddress());
            response.setStatus(201);
        } else {
            logger.error("FAILED - addFirestation POST request - Firestation number: " + firestation.getStation()+" - Firestation address: " + firestation.getAddress());
            response.setStatus(409);
        }

        return addFirestation;
    }

    @PutMapping("/firestations")
    public Firestations updateFirestation(@RequestBody Firestations firestation, HttpServletResponse response) {

        Firestations updateFirestation = firestationsServiceInterface.updateFirestation(firestation);

        if (updateFirestation != null) {
            logger.info("SUCCESS - updateFirestation PUT request - Firestation number: " + firestation.getStation()+" - Firestation address: " + firestation.getAddress());
            response.setStatus(200);
        } else {
            logger.error("FAILED - updateFirestation PUT request - Firestation number: " + firestation.getStation()+" - Firestation address: " + firestation.getAddress());
            response.setStatus(404);
        }

        return updateFirestation;
    }

    @DeleteMapping("/firestations")
    public void deleteFirestation(@RequestParam String address, HttpServletResponse response) {

        if (firestationsServiceInterface.deleteFirestation(address)){
            logger.info("SUCCESS - deleteFirestation - Firestation address :" + address + " deleted");
            response.setStatus(200);
        } else {
            logger.error("FAILED - deleteFirestation - Firestation address :" + address + " NOT deleted" );
            response.setStatus(404);
        }
    }

    @GetMapping("/phoneAlert")
    public List<String> phoneAlert(@RequestParam String firestation, HttpServletResponse response) {

         List<String> phoneAlert=firestationsServiceInterface.phoneAlert(firestation);

         if (phoneAlert != null) {
             logger.info("SUCCESS - phoneAlert - GET request for firestation number " + firestation);
             response.setStatus(200);
         } else {
             logger.error("FAILED - phoneAlert - GET request for firestation number " + firestation);
             response.setStatus(404);
         }

         return  phoneAlert;
    }

    @GetMapping("/fire")
    public List<FireDto> findFire(@RequestParam String address, HttpServletResponse response) {

        List<FireDto> findFire = firestationsServiceInterface.fire(address);

        if (findFire != null){
            logger.info("SUCCESS - findFire - GET request for address " + address);
            response.setStatus(200);
        } else {
            logger.info("FAILED - findFire - GET request for address " + address);
            response.setStatus(404);
        }
        return findFire;
    }

    @GetMapping("/flood/stations")
    public List<HouseholdDto> flood(@RequestParam List<String> stations, HttpServletResponse response){

        List<HouseholdDto> flood = firestationsServiceInterface.flood(stations);

        if (flood.size() != 0) {
            logger.info("SUCCESS = flood - GET request for firestations " + stations);
            response.setStatus(200);
        } else {
            logger.error("FAILED = flood - GET request for firestations " + stations);
            response.setStatus(404);
        }

        return flood;
    }

}
