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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonsControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testFindAll() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/persons")
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByName_WithExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/persons/info")
                .contentType(APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", "Boyd")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByName_WithNonExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/persons/info")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Toto")
                .param("lastName", "Foo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddPerson_WithNewPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Ludovic\"," +
                        "\"lastName\": \"Allegaert\"," +
                        "\"address\": \"489 Manchester St\"," +
                        "\"city\": \"Culver\"," +
                        "\"zip\": \"97451\"," +
                        "\"phone\": \"841-874-6517\"," +
                        "\"email\": \"lallegaert@email.com\"}")
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    void testAddPerson_WithExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdatePerson_WithExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/persons")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\": \"Eric\"," +
                        "\"lastName\": \"Cadigan\"," +
                        "\"address\": \"951 LoneTree Rd\"," +
                        "\"city\": \"Culver\"," +
                        "\"zip\": \"97451\"," +
                        "\"phone\": \"841-874-7459\"," +
                        "\"email\": \"gramps@email.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePerson_WithNonExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/persons")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Toto\",\"lastName\":\"Foo\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tfoo@email.com\"}"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testDeletePerson_withExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/persons")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Eric")
                .param("lastName", "Cadigan")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePerson_WithNonExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/person")
                .contentType(APPLICATION_JSON)
                .content("{\"firstName\":\"Toto\",\"Foo\":\"PERSON\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tfoo@email.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCommunityEmail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                .contentType(APPLICATION_JSON)
                .param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"drk@email.com\",\"tenz@email.com\",\"jaboyd@email.com\",\"jaboyd@email.com\",\"tcoop@ymail.com\",\"lily@email.com\",\"soph@email.com\",\"ward@email.com\",\"zarc@email.com\",\"reg@email.com\",\"jpeter@email.com\",\"jpeter@email.com\",\"aly@imail.com\",\"bstel@email.com\",\"ssanw@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"gramps@email.com\"]"))
                .andExpect(jsonPath("$.length()", is(23)));
    }

    @Test
    void TestPersonInfo_WithExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Tessa")
                .param("lastName", "Carman"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"firstName\":\"Tessa\"," +
                                "\"lastName\":\"Carman\"," +
                                "\"address\":\"834 Binoc Ave\"," +
                                "\"city\":\"Culver\"," +
                                "\"zip\":\"97451\"," +
                                "\"age\":9," +
                                "\"email\":\"tenz@email.com\"," +
                                "\"medications\":[]," +
                                "\"allergies\":[]}]"));
    }

    @Test
    void TestPersonInfo_WithNonExistingPerson() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/personInfo")
                .contentType(APPLICATION_JSON)
                .param("firstName", "Toto")
                .param("lastName", "Foo"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testListChild_WithExistingAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(APPLICATION_JSON)
                .param("address", "1509 Culver St"))
                .andExpect(status().isOk()).andExpect(content().string(
                "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"age\":37},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":32},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":9},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":3},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"age\":35}]"));
    }

    @Test
    void testListChild_WithNonExistingAddress() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                .contentType(APPLICATION_JSON)
                .param("address", "2610 Dymwis St"))
                .andExpect(status().isNotFound());
    }

}