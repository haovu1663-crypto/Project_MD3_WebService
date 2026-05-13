package re.projecwebservice.controller;

import jakarta.persistence.Entity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.MentorRepone;
import re.projecwebservice.dto.resquest.MentorRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.MentorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MentorController {
    private final MentorService mentorService;

    // Câu 15: chưa test
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STUDENT')")
    @GetMapping("/mentors")
    public ResponseEntity<?> getMentors() throws ResourceNotFoundException {
        List<MentorRepone> mentorRepones = mentorService.getMentor();
        ApiResponse<List<MentorRepone>> apiResponse = new ApiResponse<>(
                "get mentors", "200 OK", mentorRepones, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/mentors")
    public ResponseEntity<?> addMentor(@Valid @RequestBody MentorRequest mentorRequest)
            throws ResourceNotFoundException, DataConfickException {
        MentorRepone mentorRepone = mentorService.add(mentorRequest);
        ApiResponse<MentorRepone> apiResponse = new ApiResponse<>(
                "add mentor", "201 Created", mentorRepone, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR')")
    @PutMapping("/mentors/{mentor_id}")
    public ResponseEntity<?> updateMentor(
            @Valid @RequestBody MentorRequest mentorRequest,
            @PathVariable("mentor_id") Integer mentorId)
            throws ResourceNotFoundException, DataConfickException {
        MentorRepone mentorRepone = mentorService.update(mentorRequest, mentorId);
        ApiResponse<MentorRepone> apiResponse = new ApiResponse<>(
                "update mentor", "200 OK", mentorRepone, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MENTOR', 'ROLE_STUDENT')")
    @GetMapping("/mentors/{mentor_id}")
    public ResponseEntity<?> getMentorById(@PathVariable("mentor_id") Integer mentorId)
            throws ResourceNotFoundException, DataConfickException {
        MentorRepone data = mentorService.getMentorById(mentorId);
        ApiResponse<MentorRepone> apiResponse = new ApiResponse<>(
                "get mentor", "200 OK", data, null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
