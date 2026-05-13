package re.projecwebservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.projecwebservice.dto.respone.ApiResponse;
import re.projecwebservice.dto.respone.UserRespone;
import re.projecwebservice.dto.resquest.Login;
import re.projecwebservice.dto.resquest.Register;
import re.projecwebservice.entity.Users;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.service.UserService;
import re.projecwebservice.service.intf.IUserService;
import re.projecwebservice.util.Role;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return  new ResponseEntity<>(userService.login(login), HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<?> rigister(@Valid @RequestBody Register register) {
        return new ResponseEntity<>(userService.register(register), HttpStatus.CREATED);
    }
    @GetMapping("/me") // Nên thêm path /me cho rõ ràng
    public ResponseEntity<?> getMe() {
        // Truyền cả request vào service để xử lý
        return new ResponseEntity<>(userService.getMe(), HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<UserRespone> userRespones= userService.getUser();
        ApiResponse<List<UserRespone>> apiResponse = new ApiResponse<>(
                "Get user ","200 OK",userRespones,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
        UserRespone users = userService.getUserById(id);
        ApiResponse<UserRespone> apiResponse = new ApiResponse<>(
                "Get user ","200 OK",users,null, LocalDateTime.now()
        );
        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody Register register) throws ResourceNotFoundException {
        UserRespone userRespone = userService.update(register,id);
        ApiResponse<UserRespone> apiResponse = new ApiResponse<>(
                "upadte user ","200 OK",userRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/users/{id}/status")
    public ResponseEntity<?>updateStatus(@PathVariable Integer id) throws ResourceNotFoundException {
        UserRespone userRespone = userService.upadteStatus(id);
        ApiResponse<UserRespone> apiResponse = new ApiResponse<>(
                "upadte status user ","200 OK",userRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?>updateRole(@PathVariable Integer id, @RequestParam Role role) throws ResourceNotFoundException {
        UserRespone userRespone = userService.updateRole(id, role);
        ApiResponse<UserRespone> apiResponse = new ApiResponse<>(
                "upadte Role user ","200 OK",userRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?>delete(@PathVariable Integer id) throws ResourceNotFoundException {
        UserRespone userRespone = userService.delete(id);
        ApiResponse<UserRespone> apiResponse = new ApiResponse<>(
                "delete user ","200 OK",userRespone,null, LocalDateTime.now()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
