package re.projecwebservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.resquest.EvaluationCriteriaRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.EvaluationCriteriaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EvaluationCriteriaController {
    private final EvaluationCriteriaService evaluationCriteriaService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/evaluation_criteria")
    public ResponseEntity<?> addEvaluationCriteria(
            @Valid @RequestBody EvaluationCriteriaRequest request)
            throws DataConfickException {
        EvaluationCriteriaRespone data = evaluationCriteriaService.add(request);
        ApiResponse<EvaluationCriteriaRespone> apiResponse = new ApiResponse<>(
                "add evaluation criteria", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/evaluation_criteria")
    public ResponseEntity<?> getAll() {
        List<EvaluationCriteriaRespone> data = evaluationCriteriaService.getAll();
        ApiResponse<List<EvaluationCriteriaRespone>> apiResponse = new ApiResponse<>(
                "get evaluation criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/evaluation_criteria/{criterion_id}")
    public ResponseEntity<?> getById(@PathVariable("criterion_id") Integer criterionId)
            throws ResourceNotFoundException {
        EvaluationCriteriaRespone data = evaluationCriteriaService.getById(criterionId);
        ApiResponse<EvaluationCriteriaRespone> apiResponse = new ApiResponse<>(
                "get evaluation criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/evaluation_criteria/{criterion_id}")
    public ResponseEntity<?> updateEvaluationCriteria(
            @PathVariable("criterion_id") Integer criterionId,
            @RequestBody EvaluationCriteriaRequest request)
            throws DataConfickException, ResourceNotFoundException {
        EvaluationCriteriaRespone data = evaluationCriteriaService.update(criterionId, request);
        ApiResponse<EvaluationCriteriaRespone> apiResponse = new ApiResponse<>(
                "update evaluation criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/evaluation_criteria/{criterion_id}")
    public ResponseEntity<?> deleteEvaluationCriteria(
            @PathVariable("criterion_id") Integer criterionId
           ) throws ResourceNotFoundException, DataConfickException {
        EvaluationCriteriaRespone data = evaluationCriteriaService.delete(criterionId);
        ApiResponse<EvaluationCriteriaRespone> apiResponse = new ApiResponse<>(
                "delete evaluation criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
