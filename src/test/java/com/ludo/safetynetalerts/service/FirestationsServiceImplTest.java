package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dto.FireDto;
import com.ludo.safetynetalerts.dto.HouseholdDto;
import com.ludo.safetynetalerts.dto.PersonCounterDto;
import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.Firestations;
import com.ludo.safetynetalerts.model.MedicalRecords;
import com.ludo.safetynetalerts.model.Persons;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest()
class FirestationsServiceImplTest {

    @Autowired
    FirestationsServiceInterface firestationsServiceInterface;

    @MockBean
    DataBase dataBase;

    List<Firestations> firestationsList = new ArrayList<>();
    List<Persons> personsList = new ArrayList<>();
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    Persons person3 = new Persons();
    Persons person4 = new Persons();

    @BeforeAll
    public void setUp() {

        Firestations firestation1 = new Firestations();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation("3");

        Firestations firestation2 = new Firestations();
        firestation2.setAddress("112 Steppes Pl");
        firestation2.setStation("2");


        person1.setFirstName("John");
        person1.setLastName("Boyd");
        person1.setAddress("1509 Culver St");
        person1.setCity("Culver");
        person1.setZip("97451");
        person1.setPhone("841-874-6512");
        person1.setEmail("jaboyd@email.com");
        person1.setFirestations(firestation1);
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setAge(10);
        person1.setMedicalRecords(medicalRecords1);


        person2.setFirstName("Jacob");
        person2.setLastName("Boyd");
        person2.setAddress("1509 Culver St");
        person2.setCity("Culver");
        person2.setZip("97451");
        person2.setPhone("841-874-6513");
        person2.setEmail("drk@email.com");
        person2.setFirestations(firestation1);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setAge(30);
        person2.setMedicalRecords(medicalRecords2);

        person3.setFirstName("Tony");
        person3.setLastName("Cooper");
        person3.setAddress("112 Steppes Pl");
        person3.setCity("Culver");
        person3.setZip("97451");
        person3.setPhone("841-874-6874");
        person3.setEmail("tcoop@ymail.com");
        person3.setFirestations(firestation2);
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setAge(20);
        person3.setMedicalRecords(medicalRecords3);

        person4.setFirstName("Ron");
        person4.setLastName("Peters");
        person4.setAddress("112 Steppes Pl");
        person4.setCity("Culver");
        person4.setZip("97451");
        person4.setPhone("841-874-8888");
        person4.setEmail("jpeter@email.com");
        person4.setFirestations(firestation2);
        MedicalRecords medicalRecords4 = new MedicalRecords();
        medicalRecords4.setAge(30);
        person4.setMedicalRecords(medicalRecords4);

        List<Persons> personsList1 = new ArrayList<>();
        personsList1.add(person1);
        personsList1.add(person2);
        firestation1.setPersons(personsList1);

        List<Persons> personsList2 = new ArrayList<>();
        personsList2.add(person3);
        personsList2.add(person4);
        firestation2.setPersons(personsList2);

        firestationsList.add(firestation1);
        firestationsList.add(firestation2);

        personsList.add(person1);
        personsList.add(person2);
        personsList.add(person3);
        personsList.add(person4);

    }

    @BeforeEach
    public void setUpBeforeEachTest() {
        when(dataBase.getFirestations()).thenReturn(firestationsList);
        when(dataBase.getPersons()).thenReturn(personsList);
    }

    @Test
    void testFindAll() {

        List<Firestations> foundFirestation = firestationsServiceInterface.findAll();
        assertNotNull(foundFirestation);
        assertEquals(2, foundFirestation.size());
    }

    @Test
    void testFindByNumber() {

        PersonCounterDto personCounterDto = firestationsServiceInterface.findByNumber("3");

        assertNotNull(personCounterDto);
        assertEquals(2, personCounterDto.getPersonsStationList().size());
        assertEquals(1, personCounterDto.getAdultsNumber());
        assertEquals(1, personCounterDto.getChildrenNumber());

    }

    @Test
    void testPhoneAlert() {

        List<String> phoneList = firestationsServiceInterface.phoneAlert("2");

        assertNotNull(phoneList);
        assertEquals(2, phoneList.size());
        assertTrue(phoneList.contains(person3.getPhone()));
        assertTrue(phoneList.contains(person4.getPhone()));

    }

    @Test
    void testFire() {

        List<FireDto> fireList = firestationsServiceInterface.fire("1509 Culver St");

        assertNotNull(fireList);
        assertEquals(2, fireList.size());

    }

    @Test
    void testFlood() {

        List<String> stationsList = new ArrayList<>();
        stationsList.add("2");

        List<HouseholdDto> floodList = firestationsServiceInterface.flood(stationsList);

        for (HouseholdDto test: floodList) {
            System.out.println(test.getAddress());
        }


        assertNotNull(floodList);
        assertEquals(1, floodList.size());
        assertEquals("112 Steppes Pl", floodList.get(0).getAddress());

    }
}