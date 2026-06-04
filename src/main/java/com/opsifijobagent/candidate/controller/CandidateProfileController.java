package com.opsifijobagent.candidate.controller;

import com.opsifijobagent.candidate.dto.CandidateProfileCreateRequest;
import com.opsifijobagent.candidate.dto.CandidateProfileResponse;
import com.opsifijobagent.candidate.dto.CandidateProfileUpdateRequest;
import com.opsifijobagent.candidate.service.CandidateProfileService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateProfileController {

    private final CandidateProfileService candidateProfileService;

    @PostMapping
    public ResponseEntity<CandidateProfileResponse> create(@Valid @RequestBody CandidateProfileCreateRequest request) {
        CandidateProfileResponse response = candidateProfileService.create(request);
        return ResponseEntity.created(URI.create("/api/candidates/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateProfileResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(candidateProfileService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CandidateProfileResponse>> getAll() {
        return ResponseEntity.ok(candidateProfileService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateProfileResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateProfileUpdateRequest request
    ) {
        return ResponseEntity.ok(candidateProfileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        candidateProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
