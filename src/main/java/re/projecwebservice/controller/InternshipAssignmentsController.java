package re.projecwebservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.InternshipAssignmentsRespone;
import re.projecwebservice.dto.resquest.InternshipAssignmentsRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.InternshipAssignmentsService;
import re.projecwebservice.util.Status;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InternshipAssignmentsController {
    private final InternshipAssignmentsService internshipAssignmentsService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/internship_assignments")
    public ResponseEntity<?> addInternshipAssignment(
            @Valid @RequestBody InternshipAssignmentsRequest request)
            throws DataConfickException, ResourceNotFoundException {
        InternshipAssignmentsRespone data = internshipAssignmentsService.add(request);
        ApiResponse<InternshipAssignmentsRespone> apiResponse = new ApiResponse<>(
                "add internship assignment", "201 Created", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/internship_assignments")
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<InternshipAssignmentsRespone> data = internshipAssignmentsService.getAll();
        ApiResponse<List<InternshipAssignmentsRespone>> apiResponse = new ApiResponse<>(
                "get internship assignments", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/internship_assignments/{assignment_id}")
    public ResponseEntity<?> getById(@PathVariable("assignment_id") Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException {
        InternshipAssignmentsRespone data = internshipAssignmentsService.getById(assignmentId);
        ApiResponse<InternshipAssignmentsRespone> apiResponse = new ApiResponse<>(
                "get internship assignment", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/internship_assignments/{assignment_id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable("assignment_id") Integer assignmentId,
            @RequestParam Status status)
            throws ResourceNotFoundException {
        InternshipAssignmentsRespone data = internshipAssignmentsService.updateStatus(assignmentId, status);
        ApiResponse<InternshipAssignmentsRespone> apiResponse = new ApiResponse<>(
                "update internship assignment status", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
