package com.ludo.safetynetalerts.service;

import com.ludo.safetynetalerts.model.DataBase;
import com.ludo.safetynetalerts.model.MedicalRecords;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest()
class MedicalRecordsServiceImplTest {

    @Autowired
    MedicalRecordsServiceInterface medicalRecordsServiceInterface;

    @MockBean
    DataBase dataBase;

    List<MedicalRecords> medicalRecordsList = new ArrayList<>();

    @BeforeAll
    public void setup() {

        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setFirstName("John");
        medicalRecords1.setLastName("Boyd");
        medicalRecords1.setBirthdate(LocalDate.of(1984,3,6));
        medicalRecords1.setMedications(new String[]{"aznol:350mg", "hydrapermazol:100mg"});
        medicalRecords1.setAllergies(new String[]{"nillacilan"});

        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setFirstName("Jacob");
        medicalRecords2.setLastName("Boyd");
        medicalRecords2.setBirthdate(LocalDate.of(1989,3,6));
        medicalRecords2.setMedications(new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"});
        medicalRecords2.setAllergies(new String[]{});

        medicalRecordsList.add(medicalRecords1);
        medicalRecordsList.add(medicalRecords2);

    }

    @BeforeEach
    public void setUpBeforeEachTest() {
        when(dataBase.getMedicalRecords()).thenReturn(medicalRecordsList);
    }

    @Test
    void findAll() {

        List<MedicalRecords> foundMedicalRecords = medicalRecordsServiceInterface.findAll();
        assertNotNull(foundMedicalRecords);
        assertEquals(2, foundMedicalRecords.size());

    }


}