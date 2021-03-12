package com.ludo.safetynetalerts.controller;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordsControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void TestFindAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/medicalRecords")
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void TestAddMedicalRecords_WithNonExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecords")
                .contentType(APPLICATION_JSON)
                .content(" {\n"
                        + "\"firstName\":\"Ludovic\",\n"
                        + "\"lastName\":\"Allegaert\",\n"
                        + "\"birthdate\":\"06/17/1972\",\n"
                        + "\"medications\":[\"aznol:350mg\",\n"
                        + "\"hydrapermazol:100mg\"],\n"
                        + "\"allergies\":[\"nillacilan\"]\n"
                        + "}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    void TestAddMedicalRecords_WithExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecords")
                .contentType(APPLICATION_JSON)
                .content(" {\n"
                        + "\"firstName\":\"John\",\n"
                        + "\"lastName\":\"Boyd\",\n"
                        + "\"birthdate\":\"03/06/1984\",\n"
                        + "\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\n"
                        + "\"allergies\":[\"nillacilan\"]\n"
                        + "}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdateMedicalRecords_WithValidPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecords")
                .contentType(APPLICATION_JSON)
                .content(" { \r\n"
                        + "     \"firstName\":\"John\", \r\n"
                        + "     \"lastName\":\"Boyd\", \r\n"
                        + "     \"birthdate\":\"03/06/1984\", \r\n"
                        + "     \"medications\":[\"aznol:700mg\", \"hydrapermazol:200mg\"], \r\n"
                        + "     \"allergies\":[\"nillacilan\"] \r\n"
                        + "     }")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMedicalRecords_WithNonValidPerson()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
                .contentType(APPLICATION_JSON).content(" { \r\n"
                        + "     \"firstName\":\"Toto\", \r\n"
                        + "     \"lastName\":\"Foo\", \r\n"
                        + "     \"birthdate\":\"17/06/1972\", \r\n"
                        + "     \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \r\n"
                        + "     \"allergies\":[\"pollen\"] \r\n"
                        + "     }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMedicalRecords_WithValidPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecords")
                .contentType(APPLICATION_JSON).param("firstName", "John")
                .param("lastName", "Boyd")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecords_WithNonValidPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
                .param("firstName", "Toto")
                .param("lastName", "Foo"))
                .andExpect(status().isNotFound());
    }
}