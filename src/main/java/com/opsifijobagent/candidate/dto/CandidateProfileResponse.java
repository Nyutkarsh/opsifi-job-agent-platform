package com.opsifijobagent.candidate.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CandidateProfileResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        String currentTitle,
        String currentCompany,
        BigDecimal currentCtc,
        BigDecimal expectedCtc,
        Integer totalExperienceYears,
        String noticePeriod,
        List<String> preferredLocations,
        List<String> targetRoles,
        List<String> primarySkills,
        List<String> secondarySkills,
        String summary,
        Instant createdAt,
        Instant updatedAt
) {
}
