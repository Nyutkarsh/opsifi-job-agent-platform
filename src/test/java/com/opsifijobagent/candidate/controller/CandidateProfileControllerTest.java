package com.opsifijobagent.candidate.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opsifijobagent.candidate.dto.CandidateProfileCreateRequest;
import com.opsifijobagent.candidate.dto.CandidateProfileResponse;
import com.opsifijobagent.candidate.service.CandidateProfileService;
import com.opsifijobagent.common.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CandidateProfileController.class)
class CandidateProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateProfileService candidateProfileService;

    @Test
    void createShouldReturnCreatedCandidateProfile() throws Exception {
        UUID id = UUID.randomUUID();
        CandidateProfileCreateRequest request = new CandidateProfileCreateRequest(
                "Jane Doe",
                "jane.doe@example.com",
                null,
                "Software Engineer",
                null,
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(150000),
                5,
                null,
                List.of("Remote"),
                List.of("Senior Software Engineer"),
                List.of("Java"),
                List.of("Docker"),
                "Backend engineer"
        );
        CandidateProfileResponse response = new CandidateProfileResponse(
                id,
                "Jane Doe",
                "jane.doe@example.com",
                null,
                "Software Engineer",
                null,
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(150000),
                5,
                null,
                List.of("Remote"),
                List.of("Senior Software Engineer"),
                List.of("Java"),
                List.of("Docker"),
                "Backend engineer",
                Instant.now(),
                Instant.now()
        );

        when(candidateProfileService.create(any(CandidateProfileCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/candidates/" + id)))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.fullName").value("Jane Doe"));
    }

    @Test
    void createShouldReturnValidationErrorForInvalidRequest() throws Exception {
        String invalidRequest = """
                {
                  "fullName": "",
                  "email": "not-an-email",
                  "totalExperienceYears": -1,
                  "currentCtc": -1
                }
                """;

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.validationErrors.fullName").exists())
                .andExpect(jsonPath("$.validationErrors.email").exists())
                .andExpect(jsonPath("$.validationErrors.totalExperienceYears").exists())
                .andExpect(jsonPath("$.validationErrors.currentCtc").exists());

        verifyNoInteractions(candidateProfileService);
    }

    @Test
    void getByIdShouldReturnNotFoundWhenCandidateProfileDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(candidateProfileService.getById(id)).thenThrow(new ResourceNotFoundException("Candidate profile not found with id: " + id));

        mockMvc.perform(get("/api/candidates/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Candidate profile not found with id: " + id));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/candidates/{id}", id))
                .andExpect(status().isNoContent());

        verify(candidateProfileService).delete(id);
    }
}
