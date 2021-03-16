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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationsControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void findAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestations")
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void TestFindFirestationByNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                .contentType(APPLICATION_JSON).param("stationNumber", "3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void TestAddFirestation_WithNewAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/firestations")
                .contentType(APPLICATION_JSON)
                .content("{\"address\": \"2610 Dymwis St\",\"station\": \"3\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    void TestAddFirestation_WithExistingAddressAndStationNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"1509 Culver St\",\"station\": \"3\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdateFirestation_WithValidAddressAndValidNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/firestations")
                .contentType(APPLICATION_JSON)
                .content("{\"address\": \"1509 Culver St\",\"station\": \"1\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFirestation_WithValidAddressAndInvalidNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/firestations")
                .contentType(APPLICATION_JSON)
                .content("{\"address\": \"3721 Fanxot St\",\"station\": \"3\"}"))
                .andExpect(status().isNotFound());

    }

    @Test
    void TestDeleteFirestation_WithExistingAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/firestations")
                .contentType(APPLICATION_JSON).param("address", "29 15th St")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void TestDeleteFirestation_WithNonExistingAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/firestations")
                .contentType(APPLICATION_JSON).param("address", "3721 Fanxot St")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void TestPhoneAlert_WithValidStationNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(APPLICATION_JSON).param("firestation", "4"))
                .andExpect(status().isOk()).andExpect(content().string(
                "[\"841-874-6874\",\"841-874-9845\",\"841-874-8888\",\"841-874-9888\"]"));
    }

    @Test
    public void TestPhoneAlert_WithNonValidStationNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                .contentType(APPLICATION_JSON).param("firestation", "1972")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void TestFindFire_WithValidAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(APPLICATION_JSON)
                .param("address", "29 15th St"))
                .andExpect(status().isOk()).andExpect(content().string(
                "[{\"stationNumber\":\"2\"," +
                        "\"firstName\":\"Jonanathan\"," +
                        "\"lastName\":\"Marrack\"," +
                        "\"phoneNumber\":\"841-874-6513\"," +
                        "\"age\":32," +
                        "\"medications\":[]," +
                        "\"allergies\":[]}]"));
    }

    @Test
    public void TestFindFire_WithNonValidAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                .contentType(APPLICATION_JSON).param("address", "3721 Fanxot St"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFlood_WithValidStations() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(APPLICATION_JSON).param("stations", "3,4"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFlood_WithNonValidStations() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations")
                .contentType(APPLICATION_JSON).param("stations", "1972,1982"))
                .andExpect(status().isNotFound());
    }
}