package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.dto.ChildAlertDto;
import com.ludo.safetynetalerts.dto.PersonInfoDto;
import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.MedicalRecords;
import com.ludo.safetynetalerts.model.Persons;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest()
class PersonsServiceImplTest {

    @Autowired
    PersonsServiceInterface personsServiceInterface;

    @MockBean
    private DataBase dataBase;

    List<Persons> personsList = new ArrayList<>();

    @BeforeAll
    public void setup() {

        Persons person1 = new Persons();
        person1.setFirstName("John");
        person1.setLastName("Boyd");
        person1.setAddress("1509 Culver St");
        person1.setCity("Culver");
        person1.setZip("97451");
        person1.setPhone("841-874-6512");
        person1.setEmail("jaboyd@email.com");

        MedicalRecords person1MedicalRecords = new MedicalRecords();
        person1MedicalRecords.setFirstName("John");
        person1MedicalRecords.setLastName("Boyd");
        person1MedicalRecords.setBirthdate(LocalDate.of(1984,3,6));

        person1.setMedicalRecords(person1MedicalRecords);

        Persons person2 = new Persons();
        person2.setFirstName("Jacob");
        person2.setLastName("Boyd");
        person2.setAddress("1509 Culver St");
        person2.setCity("Culver");
        person2.setZip("97451");
        person2.setPhone("841-874-6513");
        person2.setEmail("drk@email.com");

        MedicalRecords person2MedicalRecords = new MedicalRecords();
        person2MedicalRecords.setFirstName("Jacob");
        person2MedicalRecords.setLastName("Boyd");
        person2MedicalRecords.setBirthdate(LocalDate.of(1989,3,6));

        person2.setMedicalRecords(person2MedicalRecords);

        Persons person3 = new Persons();
        person3.setFirstName("Tenley");
        person3.setLastName("Boyd");
        person3.setAddress("1509 Culver St");
        person3.setCity("Culver");
        person3.setZip("97451");
        person3.setPhone("841-874-6512");
        person3.setEmail("tenz@email.com");

        MedicalRecords person3MedicalRecords = new MedicalRecords();
        person3MedicalRecords.setFirstName("Tenley");
        person3MedicalRecords.setLastName("Boyd");
        person1MedicalRecords.setBirthdate(LocalDate.of(2012,2,18));

        person3.setMedicalRecords(person3MedicalRecords);

        personsList.add(person1);
        personsList.add(person2);
        personsList.add(person3);

    }

    @BeforeEach
    public void setUpBeforeEachTest() {
        when(dataBase.getPersons()).thenReturn(personsList);
    }


    @Test
    void testFindAll_thenPersonListShouldBeReturned() {

        List<Persons> foundPersons = personsServiceInterface.findAll();
        assertNotNull(foundPersons);
        assertEquals(3, foundPersons.size());

    }

    @Test
    void testFindAll_thenReturnException() {
        when(dataBase.getPersons()).thenThrow(NullPointerException.class);
        List<Persons> foundPersons = personsServiceInterface.findAll();
        assertNull(foundPersons);
    }

    @Test
    void testFindByName_ThenAPersonShouldBeReturned() {

        Persons foundPerson = personsServiceInterface.findByName("Jacob","Boyd");

        assertNotNull(foundPerson);
        assertEquals("Jacob",foundPerson.getFirstName());
        assertEquals("Boyd", foundPerson.getLastName());

    }

    @Test
    void testFindByName_thenReturnException() {
        when(dataBase.getPersons()).thenThrow(NullPointerException.class);
        Persons foundPerson = personsServiceInterface.findByName("Jacob","Boyd");
        assertNull(foundPerson);
    }

    @Test
    void testFindChild_ThenReturnChildAndAdultsList() {
        List<ChildAlertDto> foundChild = personsServiceInterface.findChild("1509 Culver St");

        assertNotNull(foundChild);
        assertEquals(3, foundChild.size());
    }

    @Test
    void testFindChild_thenReturnException() {
        when(dataBase.getPersons()).thenThrow(NullPointerException.class);
        List<ChildAlertDto> foundChild = personsServiceInterface.findChild("1509 Culver St");
        assertNull(foundChild);
    }


    @Test
    void testpersonInfo() {

        List<PersonInfoDto> foundPerson = personsServiceInterface.personInfo("John","Boyd");

        assertNotNull(foundPerson);
        assertEquals(3, foundPerson.size());
    }

    @Test
    void testpersonInfo_thenReturnException() {
        when(dataBase.getPersons()).thenThrow(NullPointerException.class);
        List<PersonInfoDto> foundPerson = personsServiceInterface.personInfo("John","Boyd");
        assertNull(foundPerson);
    }

    @Test
    void testCommunityEmail() {

        List<String> personsEmail = personsServiceInterface.communityEmail("Culver");

        assertNotNull(personsEmail);
        assertEquals(3, personsEmail.size());
    }

    @Test
    void estCommunityEmail_thenReturnException() {
        when(dataBase.getPersons()).thenThrow(NullPointerException.class);
        List<String> personsEmail = personsServiceInterface.communityEmail("Culver");
        assertNull(personsEmail);
    }
}