package com.opsifijobagent.candidate.service;

import com.opsifijobagent.candidate.dto.CandidateProfileCreateRequest;
import com.opsifijobagent.candidate.dto.CandidateProfileResponse;
import com.opsifijobagent.candidate.dto.CandidateProfileUpdateRequest;
import com.opsifijobagent.candidate.entity.CandidateProfile;
import com.opsifijobagent.candidate.repository.CandidateProfileRepository;
import com.opsifijobagent.common.exception.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;

    @Transactional
    public CandidateProfileResponse create(CandidateProfileCreateRequest request) {
        CandidateProfile candidateProfile = CandidateProfile.builder()
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .currentTitle(request.currentTitle())
                .currentCompany(request.currentCompany())
                .currentCtc(request.currentCtc())
                .expectedCtc(request.expectedCtc())
                .totalExperienceYears(request.totalExperienceYears())
                .noticePeriod(request.noticePeriod())
                .preferredLocations(copyList(request.preferredLocations()))
                .targetRoles(copyList(request.targetRoles()))
                .primarySkills(copyList(request.primarySkills()))
                .secondarySkills(copyList(request.secondarySkills()))
                .summary(request.summary())
                .build();

        return toResponse(candidateProfileRepository.save(candidateProfile));
    }

    @Transactional(readOnly = true)
    public CandidateProfileResponse getById(UUID id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<CandidateProfileResponse> getAll() {
        return candidateProfileRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CandidateProfileResponse update(UUID id, CandidateProfileUpdateRequest request) {
        CandidateProfile candidateProfile = findById(id);

        if (request.fullName() != null) {
            candidateProfile.setFullName(request.fullName());
        }
        if (request.email() != null) {
            candidateProfile.setEmail(request.email());
        }
        if (request.phone() != null) {
            candidateProfile.setPhone(request.phone());
        }
        if (request.currentTitle() != null) {
            candidateProfile.setCurrentTitle(request.currentTitle());
        }
        if (request.currentCompany() != null) {
            candidateProfile.setCurrentCompany(request.currentCompany());
        }
        if (request.currentCtc() != null) {
            candidateProfile.setCurrentCtc(request.currentCtc());
        }
        if (request.expectedCtc() != null) {
            candidateProfile.setExpectedCtc(request.expectedCtc());
        }
        if (request.totalExperienceYears() != null) {
            candidateProfile.setTotalExperienceYears(request.totalExperienceYears());
        }
        if (request.noticePeriod() != null) {
            candidateProfile.setNoticePeriod(request.noticePeriod());
        }
        if (request.preferredLocations() != null) {
            candidateProfile.setPreferredLocations(copyList(request.preferredLocations()));
        }
        if (request.targetRoles() != null) {
            candidateProfile.setTargetRoles(copyList(request.targetRoles()));
        }
        if (request.primarySkills() != null) {
            candidateProfile.setPrimarySkills(copyList(request.primarySkills()));
        }
        if (request.secondarySkills() != null) {
            candidateProfile.setSecondarySkills(copyList(request.secondarySkills()));
        }
        if (request.summary() != null) {
            candidateProfile.setSummary(request.summary());
        }

        return toResponse(candidateProfileRepository.save(candidateProfile));
    }

    @Transactional
    public void delete(UUID id) {
        CandidateProfile candidateProfile = findById(id);
        candidateProfileRepository.delete(candidateProfile);
    }

    private CandidateProfile findById(UUID id) {
        return candidateProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate profile not found with id: " + id));
    }

    private CandidateProfileResponse toResponse(CandidateProfile candidateProfile) {
        return new CandidateProfileResponse(
                candidateProfile.getId(),
                candidateProfile.getFullName(),
                candidateProfile.getEmail(),
                candidateProfile.getPhone(),
                candidateProfile.getCurrentTitle(),
                candidateProfile.getCurrentCompany(),
                candidateProfile.getCurrentCtc(),
                candidateProfile.getExpectedCtc(),
                candidateProfile.getTotalExperienceYears(),
                candidateProfile.getNoticePeriod(),
                copyList(candidateProfile.getPreferredLocations()),
                copyList(candidateProfile.getTargetRoles()),
                copyList(candidateProfile.getPrimarySkills()),
                copyList(candidateProfile.getSecondarySkills()),
                candidateProfile.getSummary(),
                candidateProfile.getCreatedAt(),
                candidateProfile.getUpdatedAt()
        );
    }

    private List<String> copyList(List<String> values) {
        if (values == null) {
            return new ArrayList<>();
        }

        return values.stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }
}
