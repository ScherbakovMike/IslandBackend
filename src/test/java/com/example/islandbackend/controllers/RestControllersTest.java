package com.example.islandbackend.controllers;

import com.example.islandbackend.models.characteristics.CharacteristicsHelpers;
import com.example.islandbackend.services.IslandService;
import com.example.islandbackend.services.SessionService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static com.example.islandbackend.models.characteristics.IslandCharacteristics.defaultHeight;
import static com.example.islandbackend.models.characteristics.IslandCharacteristics.defaultWidth;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class RestControllersTest {

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

    private static String sessionId;
    private static String stepId;
    private static String islandId;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("Session")
    @Order(1)
    class SessionTest {
        @Test
        @Order(1)
        @DisplayName("Init session")
        void sessionsInit() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/sessions/init")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sessionId").exists())
                    .andExpect(jsonPath("$.stepId").exists())
                    .andExpect(jsonPath("$.islandId").exists())
                    .andExpect(jsonPath("$.startTime").exists())
                    .andExpect(jsonPath("$.endTime").exists())
                    .andReturn();

            sessionId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.sessionId");
            stepId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.stepId");
            islandId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.islandId");
            Assertions.assertEquals(36, sessionId.length());
            Assertions.assertEquals(36, stepId.length());
            Assertions.assertEquals(36, islandId.length());
        }

        @Order(2)
        @Test
        @DisplayName("Session info")
        void sessionInfo() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/sessions/" + sessionId + "/info")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sessionId").exists())
                    .andExpect(jsonPath("$.stepId").exists())
                    .andExpect(jsonPath("$.islandId").exists())
                    .andExpect(jsonPath("$.startTime").exists())
                    .andExpect(jsonPath("$.endTime").exists())
                    .andReturn();

            sessionId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.sessionId");
            stepId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.stepId");
            islandId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.islandId");
            Assertions.assertEquals(36, sessionId.length());
            Assertions.assertEquals(36, stepId.length());
            Assertions.assertEquals(36, islandId.length());
        }
    }

    @Nested
    @DisplayName("Step")
    @Order(2)
    class StepTest {
        @Test
        @Order(1)
        @DisplayName("Step info")
        void stepInfo() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/steps/" + stepId + "/info")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sessionId").exists())
                    .andExpect(jsonPath("$.stepId").exists())
                    .andExpect(jsonPath("$.islandId").exists())
                    .andExpect(jsonPath("$.stepNumber").exists())
                    .andReturn();
        }

        @Order(3)
        @Test
        @DisplayName("Next step")
        void sessionNextStep() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/sessions/" + sessionId + "/next")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sessionId").exists())
                    .andExpect(jsonPath("$.stepId").exists())
                    .andExpect(jsonPath("$.islandId").exists())
                    .andExpect(jsonPath("$.stepNumber").exists())
                    .andExpect(jsonPath("$.changes").exists())
                    .andReturn();

            Map<String, String> changes = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.changes");
            Assertions.assertEquals(changes.size(), CharacteristicsHelpers.kindsOfEntities().size());
        }
    }

    @Nested
    @DisplayName("Island")
    @Order(3)
    class IslandTest {
        @Order(1)
        @Test
        @DisplayName("Island info")
        void islandInfo() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/islands/" + islandId + "/info")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sessionId").exists())
                    .andExpect(jsonPath("$.stepId").exists())
                    .andExpect(jsonPath("$.islandId").exists())
                    .andExpect(jsonPath("$.width").exists())
                    .andExpect(jsonPath("$.height").exists())
                    .andReturn();
        }

        @Order(2)
        @Test
        @DisplayName("Island summary")
        void islandSummary() throws Exception {
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/islands/" + islandId + "/summary")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn();
            Map<String, Map<String, Long>> changes = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.stats");
            Assertions.assertEquals(changes.size(), defaultWidth * defaultHeight);
        }
    }
}