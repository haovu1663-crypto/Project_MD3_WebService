package re.projecwebservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.StudentRespone;
import re.projecwebservice.dto.respone.UserRespone;
import re.projecwebservice.dto.resquest.StudentRequest;
import re.projecwebservice.entity.Students;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.StudentService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentContoller {
    private final StudentService studentService;
    // câu 10 làm sau
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MENTOR')")
    @GetMapping("/students")
    public ResponseEntity<?> getStudents() throws ResourceNotFoundException {
        List<StudentRespone> studentRespone = studentService.getStudent();
        ApiResponse<List<StudentRespone>> apiResponse = new ApiResponse<>(
                " get student ","201 ceate",studentRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    // 11
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MENTOR','ROLE_STUDENT')")
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
        StudentRespone studentRespone = studentService.getStudentById(id);
        ApiResponse<StudentRespone> apiResponse = new ApiResponse<>(
                " get student ","201 ceate",studentRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    // câu 12 add
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentRequest student) throws ResourceNotFoundException, DataConfickException {
        StudentRespone students= studentService.add(student);
        ApiResponse<StudentRespone> apiResponse = new ApiResponse<>(
                " add student ","201 ceate",students,null, LocalDateTime.now()
        );
        return new  ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_STUDENT')")
    @PutMapping("/students/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody StudentRequest student, @PathVariable Integer id ) throws ResourceNotFoundException, DataConfickException {
        StudentRespone students= studentService.update(student,id);
        ApiResponse<StudentRespone> apiResponse = new ApiResponse<>(
                " update student ","200 Ok",students,null, LocalDateTime.now()
        );
        return new  ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
