package com.opsifijobagent.candidate.repository;

import com.opsifijobagent.candidate.entity.CandidateProfile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, UUID> {
}
