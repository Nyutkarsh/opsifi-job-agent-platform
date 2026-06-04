package com.opsifijobagent.candidate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opsifijobagent.candidate.dto.CandidateProfileCreateRequest;
import com.opsifijobagent.candidate.dto.CandidateProfileResponse;
import com.opsifijobagent.candidate.dto.CandidateProfileUpdateRequest;
import com.opsifijobagent.candidate.entity.CandidateProfile;
import com.opsifijobagent.candidate.repository.CandidateProfileRepository;
import com.opsifijobagent.common.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CandidateProfileServiceTest {

    @Mock
    private CandidateProfileRepository candidateProfileRepository;

    @InjectMocks
    private CandidateProfileService candidateProfileService;

    @Test
    void createShouldPersistCandidateProfile() {
        CandidateProfileCreateRequest request = new CandidateProfileCreateRequest(
                "Jane Doe",
                "jane.doe@example.com",
                "+1-555-0100",
                "Software Engineer",
                "Example Inc",
                BigDecimal.valueOf(120000),
                BigDecimal.valueOf(150000),
                5,
                "30 days",
                List.of(" Remote ", ""),
                List.of("Senior Software Engineer"),
                List.of("Java", "Spring Boot"),
                List.of("Docker"),
                "Backend engineer"
        );
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        when(candidateProfileRepository.save(any(CandidateProfile.class))).thenAnswer(invocation -> {
            CandidateProfile candidateProfile = invocation.getArgument(0);
            candidateProfile.setId(id);
            candidateProfile.setCreatedAt(now);
            candidateProfile.setUpdatedAt(now);
            return candidateProfile;
        });

        CandidateProfileResponse response = candidateProfileService.create(request);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.fullName()).isEqualTo("Jane Doe");
        assertThat(response.email()).isEqualTo("jane.doe@example.com");
        assertThat(response.preferredLocations()).containsExactly("Remote");
        verify(candidateProfileRepository).save(any(CandidateProfile.class));
    }

    @Test
    void updateShouldModifyOnlyProvidedFields() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        CandidateProfile existing = CandidateProfile.builder()
                .id(id)
                .fullName("Jane Doe")
                .email("jane.doe@example.com")
                .phone("123")
                .totalExperienceYears(5)
                .primarySkills(List.of("Java"))
                .createdAt(now)
                .updatedAt(now)
                .build();
        CandidateProfileUpdateRequest request = new CandidateProfileUpdateRequest(
                null,
                "jane.updated@example.com",
                null,
                null,
                null,
                null,
                null,
                6,
                null,
                null,
                null,
                List.of("Java", "PostgreSQL"),
                null,
                null
        );

        when(candidateProfileRepository.findById(id)).thenReturn(Optional.of(existing));
        when(candidateProfileRepository.save(existing)).thenReturn(existing);

        CandidateProfileResponse response = candidateProfileService.update(id, request);

        assertThat(response.fullName()).isEqualTo("Jane Doe");
        assertThat(response.email()).isEqualTo("jane.updated@example.com");
        assertThat(response.phone()).isEqualTo("123");
        assertThat(response.totalExperienceYears()).isEqualTo(6);
        assertThat(response.primarySkills()).containsExactly("Java", "PostgreSQL");
    }

    @Test
    void getByIdShouldThrowWhenCandidateProfileDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(candidateProfileRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateProfileService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
