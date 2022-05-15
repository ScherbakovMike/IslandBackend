package com.example.islandbackend.controllers;

import com.example.islandbackend.services.IslandService;
import com.example.islandbackend.services.SessionService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class RestControllersTest {

    private static String sessionId;
    private static String stepId;
    private static String islandId;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionController sessionController;
    @Autowired
    private IslandService islandService;
    @Autowired
    private IslandController islandController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void sessionsInit() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/sessions/init")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.currentStep").exists())
                .andExpect(jsonPath("$.islandId").exists())
                .andExpect(jsonPath("$.startTime").exists())
                .andExpect(jsonPath("$.endTime").exists())
                .andReturn();

        sessionId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
        stepId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.currentStep");
        islandId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.islandId");
        Assertions.assertEquals(36, sessionId.length());
        Assertions.assertEquals(36, stepId.length());
        Assertions.assertEquals(36, islandId.length());
    }

    @Test
    void islandSummary() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/islands/" + islandId + "/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
    }

}