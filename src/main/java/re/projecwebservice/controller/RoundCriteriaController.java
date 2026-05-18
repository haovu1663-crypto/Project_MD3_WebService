package re.projecwebservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.RoundCriteriaRespone;
import re.projecwebservice.dto.resquest.RoundCriteriaRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.RoundCriteriaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoundCriteriaController {
    private final RoundCriteriaService roundCriteriaService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/round_criteria")
    public ResponseEntity<?> addRoundCriteria(
            @Valid @RequestBody RoundCriteriaRequest request)
            throws DataConfickException, ResourceNotFoundException {
        RoundCriteriaRespone data = roundCriteriaService.add(request);
        ApiResponse<RoundCriteriaRespone> apiResponse = new ApiResponse<>(
                "add round criteria", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/round_criteria")
    public ResponseEntity<?> getAllByRoundId(@RequestParam("round_id") Integer roundId)
            throws ResourceNotFoundException {
        List<RoundCriteriaRespone> data = roundCriteriaService.getAllByRoundId(roundId);
        ApiResponse<List<RoundCriteriaRespone>> apiResponse = new ApiResponse<>(
                "get round criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/round_criteria/{round_criterion_id}")
    public ResponseEntity<?> getById(@PathVariable("round_criterion_id") Integer roundCriterionId)
            throws ResourceNotFoundException {
        RoundCriteriaRespone data = roundCriteriaService.getById(roundCriterionId);
        ApiResponse<RoundCriteriaRespone> apiResponse = new ApiResponse<>(
                "get round criterion", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/round_criteria/{round_criterion_id}")
    public ResponseEntity<?> updateWeight(
            @PathVariable("round_criterion_id") Integer roundCriterionId,
            @Valid @RequestBody RoundCriteriaRequest request)
            throws ResourceNotFoundException, DataConfickException {
        RoundCriteriaRespone data = roundCriteriaService.update(roundCriterionId, request);
        ApiResponse<RoundCriteriaRespone> apiResponse = new ApiResponse<>(
                "update round criteria weight", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/round_criteria/{round_criterion_id}")
    public ResponseEntity<?> delete(
            @PathVariable("round_criterion_id") Integer roundCriterionId)
            throws ResourceNotFoundException {
        RoundCriteriaRespone data = roundCriteriaService.delete(roundCriterionId);
        ApiResponse<RoundCriteriaRespone> apiResponse = new ApiResponse<>(
                "delete round criteria", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
