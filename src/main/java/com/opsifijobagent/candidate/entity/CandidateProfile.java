package com.opsifijobagent.candidate.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "candidate_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateProfile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    private String phone;
    private String currentTitle;
    private String currentCompany;
    private BigDecimal currentCtc;
    private BigDecimal expectedCtc;
    private Integer totalExperienceYears;
    private String noticePeriod;

    @ElementCollection
    @CollectionTable(name = "candidate_profile_preferred_locations", joinColumns = @JoinColumn(name = "candidate_profile_id"))
    @Column(name = "location")
    @Builder.Default
    private List<String> preferredLocations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "candidate_profile_target_roles", joinColumns = @JoinColumn(name = "candidate_profile_id"))
    @Column(name = "role")
    @Builder.Default
    private List<String> targetRoles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "candidate_profile_primary_skills", joinColumns = @JoinColumn(name = "candidate_profile_id"))
    @Column(name = "skill")
    @Builder.Default
    private List<String> primarySkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "candidate_profile_secondary_skills", joinColumns = @JoinColumn(name = "candidate_profile_id"))
    @Column(name = "skill")
    @Builder.Default
    private List<String> secondarySkills = new ArrayList<>();

    @Column(length = 2000)
    private String summary;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
