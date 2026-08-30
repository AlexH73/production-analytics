package com.production.analytics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("EventController integration tests")
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void cleanDatabase() {
        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/events: should return 201 and created event")
    void createEvent_shouldReturn201() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "MACHINE_STARTED",
                "Линия запущена",
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value("MACHINE_STARTED"));
    }

    @Test
    @DisplayName("POST /api/events: should return 400 when type is empty")
    void createEvent_emptyType_shouldReturn400() throws Exception {
        String body = """
                {
                  "type": "",
                  "description": "desc",
                  "occurredAt": "2026-08-23T10:30:00"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/api/events"))
                .andExpect(jsonPath("$.fieldErrors.type").exists());
    }

    @Test
    @DisplayName("POST /api/events: should accept a description of 2000 characters")
    void createEvent_descriptionAtMaxLength_shouldReturn201() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "MACHINE_STARTED",
                "a".repeat(2000),
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("a".repeat(2000)));
    }

    @Test
    @DisplayName("POST /api/events: should return 400 when description exceeds 2000 characters")
    void createEvent_descriptionExceedsMaxLength_shouldReturn400() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "MACHINE_STARTED",
                "a".repeat(2001),
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.description").exists());
    }

    @Test
    @DisplayName("POST /api/events: should return 400 when type exceeds 50 characters")
    void createEvent_typeExceedsMaxLength_shouldReturn400() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "a".repeat(51),
                "description",
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.type").exists());
    }

    @Test
    @DisplayName("POST /api/events: should return 400 when occurredAt is missing")
    void createEvent_missingOccurredAt_shouldReturn400() throws Exception {
        String body = """
                {
                  "type": "MACHINE_STARTED",
                  "description": "desc"
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.occurredAt").exists());
    }

    @Test
    @DisplayName("GET /api/events/{id}: should return 404 for unknown id")
    void getUnknownEvent_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/events/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Event with id 999999 not found"))
                .andExpect(jsonPath("$.path").value("/api/events/999999"));
    }

    @Test
    @DisplayName("GET /api/events: should return 200")
    void getAll_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }
}
