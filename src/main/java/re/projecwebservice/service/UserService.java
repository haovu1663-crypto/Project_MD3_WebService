package re.projecwebservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import re.projecwebservice.config.jwt.JwtService;
import re.projecwebservice.dto.respone.JwtRespone;
import re.projecwebservice.dto.respone.RegisterRespone;
import re.projecwebservice.dto.respone.UserRespone;
import re.projecwebservice.dto.resquest.Login;
import re.projecwebservice.dto.resquest.Register;
import re.projecwebservice.entity.Users;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.UserRespository;
import re.projecwebservice.service.intf.IUserService;
import re.projecwebservice.util.Role;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final AuthenticationManager authenticationManager;
    private final UserRespository userRespository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtRespone login(Login login) {
        Authentication authentication = null;
        try{
            // phương thức kiểm tra
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        }catch (Exception e){
            e.printStackTrace(); // ← thêm dòng này
            throw new RuntimeException("mk haoc tk sai ");
        }
        // trả vè jwtRespone
        Users user  = userRespository.findByUsername(login.getUsername()).orElseThrow(()->new RuntimeException("username not found"));


        JwtRespone res =  JwtRespone.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .accessToken(jwtService.generateAccessToken(user.getUsername()))
                .expirationDate(new Date(new Date().getTime()+15*60*1000))
                .refreshToken(null)
                .role(user.getRole().name())
                .build();
        return res;
    }

    @Override
    public RegisterRespone register(Register register) throws DataConfickException {
        Users user = modelMapper.map(register, Users.class);
        if(userRespository.existsByEmail(register.getEmail())){
            throw new DataConfickException("Email :"+register.getEmail()+" Đã ồn tại ");
        }
        user.setPasswordHash(passwordEncoder.encode(register.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setIsActive(true);
        userRespository.save(user);
        return modelMapper.map(register, RegisterRespone.class);
    }

    @Override
    public RegisterRespone getMe() {
        // Spring Security đã lưu sẵn thông tin user đăng nhập ở đây
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Users user = userRespository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user, RegisterRespone.class);
    }

    @Override
    public List<UserRespone> getUser() {
        List<Users> users = userRespository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserRespone.class)) // Chuyển đổi đối tượng
                .toList();
    }

    @Override
    public UserRespone getUserById(Integer id) throws ResourceNotFoundException {
       Users user = userRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("user dont exit"));
       return modelMapper.map(user, UserRespone.class);
    }

    @Override
    public UserRespone update(Register register,Integer id) throws ResourceNotFoundException, DataConfickException {
        Users users = userRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Dont Exit"));
        Users checkEmail =userRespository.findByEmail(register.getEmail());
        if(checkEmail!=null && !checkEmail.getUserId().equals(users.getUserId())){
            throw new DataConfickException("Email :"+register.getEmail()+" Đã ồn tại ");
        }
        users.setFullName(register.getFullName());
        users.setPasswordHash(passwordEncoder.encode(register.getPasswordHash()));
        users.setRole(register.getRole());
        users.setUpdatedAt(LocalDateTime.now());
        users.setIsActive(true);
        users.setEmail(register.getEmail());
        users.setPhoneNumber(register.getPhoneNumber());
        users.setUserId(id);
        users.setUsername(register.getUsername());
        userRespository.save(users);
       return modelMapper.map(users, UserRespone.class);
    }

    @Override
    public UserRespone upadteStatus(Integer id) throws ResourceNotFoundException {
        Users users = userRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Dont Exit"));
        users.setIsActive(true);
        users.setUserId(id);
        users.setUpdatedAt(LocalDateTime.now());
        userRespository.save(users);
        return modelMapper.map(users, UserRespone.class);
    }

    @Override
    public UserRespone updateRole(Integer id,Role role) throws ResourceNotFoundException {
        Users users= userRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Dont Exit"));
        if(users.getRole()==Role.ROLE_ADMIN){
            throw new RuntimeException("không thể thay đổi quền cua các admin khác ");
        }
        users.setRole(role);
        userRespository.save(users);
        return modelMapper.map(users, UserRespone.class);
    }

    @Override
    public UserRespone delete(Integer id) throws ResourceNotFoundException {
        Users users = userRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("User Dont Exit"));
        UserRespone userRespone = modelMapper.map(users, UserRespone.class);
        userRespository.delete(users);
        return userRespone;
    }
}
