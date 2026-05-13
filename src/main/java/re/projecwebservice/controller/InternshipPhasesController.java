package re.projecwebservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.ModelMapper.InternshipPhasesMapper;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.InternshipPhasesRespone;
import re.projecwebservice.dto.resquest.InternshipPhasesRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.InternshipPhasesService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InternshipPhasesController {
    private final InternshipPhasesService internshipPhasesService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/internship_phases")
    public ResponseEntity<?> addInternshipPhase(
            @Valid @RequestBody InternshipPhasesRequest request)
            throws DataConfickException, ResourceNotFoundException {
        InternshipPhasesRespone data = internshipPhasesService.add(request);
        ApiResponse<InternshipPhasesRespone> apiResponse = new ApiResponse<>(
                "add internship phase", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/internship_phases")
    public ResponseEntity<?> getAll() {
        List<InternshipPhasesRespone> data = internshipPhasesService.getAll();
        ApiResponse<List<InternshipPhasesRespone>> apiResponse = new ApiResponse<>(
                "get internship phases", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/internship_phases/{phase_id}")
    public ResponseEntity<?> getById(@PathVariable("phase_id") Integer phaseId)
            throws ResourceNotFoundException {
        InternshipPhasesRespone data = internshipPhasesService.getById(phaseId);
        ApiResponse<InternshipPhasesRespone> apiResponse = new ApiResponse<>(
                "get internship phase", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/internship_phases/{phase_id}")
    public ResponseEntity<?> updateInternshipPhase(
            @PathVariable("phase_id") Integer phaseId,
            @RequestBody InternshipPhasesRequest request)
            throws DataConfickException, ResourceNotFoundException {
        InternshipPhasesRespone data = internshipPhasesService.update(phaseId, request);
        ApiResponse<InternshipPhasesRespone> apiResponse = new ApiResponse<>(
                "update internship phase", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/internship_phases/{phase_id}")
    public ResponseEntity<?> deleteInternshipPhase(
            @PathVariable("phase_id") Integer phaseId)
            throws ResourceNotFoundException {
        InternshipPhasesRespone data = internshipPhasesService.delete(phaseId);
        ApiResponse<InternshipPhasesRespone> apiResponse = new ApiResponse<>(
                "delete internship phase", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
