package re.projecwebservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.AssessmentRoundsRespone;
import re.projecwebservice.dto.resquest.AssessmentRoundsResquest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.AssessmentRoundsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AssessmentRoundsController {
    private final AssessmentRoundsService assessmentRoundsService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/assessment_rounds")
    public ResponseEntity<?> addAssessmentRound(
            @Valid @RequestBody AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentRoundsRespone data = assessmentRoundsService.add(request);
        ApiResponse<AssessmentRoundsRespone> apiResponse = new ApiResponse<>(
                "add assessment round", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/assessment_rounds")
    public ResponseEntity<?> getAll() {
        List<AssessmentRoundsRespone> data = assessmentRoundsService.getAll();
        ApiResponse<List<AssessmentRoundsRespone>> apiResponse = new ApiResponse<>(
                "get assessment rounds", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/assessment_rounds/{round_id}")
    public ResponseEntity<?> getById(@PathVariable("round_id") Integer roundId)
            throws ResourceNotFoundException {
        AssessmentRoundsRespone data = assessmentRoundsService.getById(roundId);
        ApiResponse<AssessmentRoundsRespone> apiResponse = new ApiResponse<>(
                "get assessment round", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/assessment_rounds/{round_id}")
    public ResponseEntity<?> updateAssessmentRound(
             @PathVariable("round_id") Integer roundId,
            @Valid  @RequestBody AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentRoundsRespone data = assessmentRoundsService.update(roundId, request);
        ApiResponse<AssessmentRoundsRespone> apiResponse = new ApiResponse<>(
                "update assessment round", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/assessment_rounds/{round_id}")
    public ResponseEntity<?> deleteAssessmentRound(
            @PathVariable("round_id") Integer roundId)
            throws ResourceNotFoundException {
        AssessmentRoundsRespone data = assessmentRoundsService.delete(roundId);
        ApiResponse<AssessmentRoundsRespone> apiResponse = new ApiResponse<>(
                "delete assessment round", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
