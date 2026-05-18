package re.projecwebservice.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.AssessmentResultsRespone;
import re.projecwebservice.dto.resquest.AssessmentResultsRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.AssessmentResultsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AssessmentResultsController {
    private final AssessmentResultsService assessmentResultsService;

    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
    @PostMapping("/assessment_results")
    public ResponseEntity<?> addAssessmentResult(
            @Valid @RequestBody AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentResultsRespone data = assessmentResultsService.add(request);
        ApiResponse<AssessmentResultsRespone> apiResponse = new ApiResponse<>(
                "add assessment result", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/assessment_results")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "assignment_id", required = false) Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException {
        List<AssessmentResultsRespone> data = assessmentResultsService.getAll(assignmentId);
        ApiResponse<List<AssessmentResultsRespone>> apiResponse = new ApiResponse<>(
                "get assessment results", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
    @PutMapping("/assessment_results/{result_id}")
    public ResponseEntity<?> update(
            @PathVariable("result_id") Integer resultId,
            @RequestBody AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentResultsRespone data = assessmentResultsService.update(resultId, request);
        ApiResponse<AssessmentResultsRespone> apiResponse = new ApiResponse<>(
                "update assessment result", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
