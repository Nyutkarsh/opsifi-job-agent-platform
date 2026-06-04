package com.opsifijobagent.candidate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record CandidateProfileCreateRequest(
        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String phone,
        String currentTitle,
        String currentCompany,

        @PositiveOrZero(message = "Current CTC must be greater than or equal to 0")
        BigDecimal currentCtc,

        @PositiveOrZero(message = "Expected CTC must be greater than or equal to 0")
        BigDecimal expectedCtc,

        @Min(value = 0, message = "Total experience years must be greater than or equal to 0")
        Integer totalExperienceYears,

        String noticePeriod,
        List<String> preferredLocations,
        List<String> targetRoles,
        List<String> primarySkills,
        List<String> secondarySkills,

        @Size(max = 2000, message = "Summary must not exceed 2000 characters")
        String summary
) {
}
