package re.projecwebservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import re.projecwebservice.dto.respone.StudentRespone;
import re.projecwebservice.dto.respone.UserRespone;
import re.projecwebservice.dto.resquest.StudentRequest;
import re.projecwebservice.entity.Students;
import re.projecwebservice.entity.Users;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.StudentRespository;
import re.projecwebservice.respository.UserRespository;
import re.projecwebservice.service.intf.IStudentService;
import re.projecwebservice.util.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final UserRespository userRespository;
    private final StudentRespository studentRespository;
    private final ModelMapper modelMapper;
    @Override
    public List<StudentRespone> getStudent() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users user = userRespository.findByUsername(currentUsername).orElseThrow();
        if(user.getRole() == Role.ROLE_ADMIN){
            List<Students> students = studentRespository.findAll();
            return students.stream().map(student -> modelMapper.map(student, StudentRespone.class)).toList();
        }
        else  {
            List<Students> students = studentRespository.findStudentsByMentorId(user.getUserId());
            return students.stream().map(student -> modelMapper.map(student, StudentRespone.class)).toList();
        }
    }

    @Override
    public StudentRespone add(StudentRequest studentRequest) throws ResourceNotFoundException, DataConfickException {
        // 1. Tìm user theo studentId
        Users user = userRespository.findById(studentRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản user này, add student thất bại"));

        // 2. Kiểm tra student đã tồn tại chưa — dùng existsById thay vì findByStudentId trả boolean primitive
        if (studentRespository.existsById(studentRequest.getStudentId())) {
            throw new DataConfickException("Student này đã được đăng ký rồi");
        }

        // 3. Kiểm tra studentCode trùng — dùng existsByStudentCode thay vì findByStudentCode trả boolean primitive
        if (studentRespository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DataConfickException("Mã Student này đã tồn tại");
        }

        // 4. Tạo Students thủ công — KHÔNG dùng ModelMapper để tránh lỗi null identifier với @MapsId
        Students student = new Students();
        student.setUser(user);
        student.setStudentCode(studentRequest.getStudentCode());
        student.setMajor(studentRequest.getMajor());
        student.setClazz(studentRequest.getClazz());
        student.setDateOfBirth(studentRequest.getDateOfBirth());
        student.setAddress(studentRequest.getAddress());

        // 5. Lưu
        studentRespository.save(student);

        // 6. Map thủ công sang StudentRespone (field user_id không map được tự động qua ModelMapper)
        StudentRespone response = new StudentRespone();
        response.setStudentId(student.getStudentId());
        response.setStudentCode(student.getStudentCode());
        response.setMajor(student.getMajor());
        response.setClazz(student.getClazz());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setAddress(student.getAddress());

        return response;
    }

    @Override
    public StudentRespone getStudentById(Integer id) throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users user = userRespository.findByUsername(currentUsername).orElseThrow();
        if(user.getRole() == Role.ROLE_STUDENT){
            if(id.equals(user.getUserId())){
                Students student = studentRespository.findById(id).orElseThrow(()-> new ResourceNotFoundException("không tìm thấy Student này"));
                StudentRespone response = modelMapper.map(student, StudentRespone.class);
                return response ;
            }
            else {
                throw new ResourceNotFoundException("Bạn không thể xem thông tin của người khác  ");
            }
        }
        else {
            Students students = studentRespository.findById(id).orElseThrow(()->new ResourceNotFoundException(" không tìm thấy student này (admin,mentor) "));
            return modelMapper.map(students, StudentRespone.class);
        }
    }

    @Override
    public StudentRespone update(StudentRequest studentRequest, Integer id)
            throws ResourceNotFoundException, DataConfickException {

        // 1. Kiểm tra student tồn tại
        Students student = studentRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student này không tồn tại"));

        // 2. Kiểm tra studentCode trùng với student KHÁC
        if (studentRespository.existsByStudentCode(studentRequest.getStudentCode())
                && !student.getStudentCode().equals(studentRequest.getStudentCode())) {
            throw new DataConfickException("Mã Student này đã tồn tại");
        }

        // 3. Cập nhật các field
        student.setStudentCode(studentRequest.getStudentCode());
        student.setMajor(studentRequest.getMajor());
        student.setClazz(studentRequest.getClazz());
        student.setDateOfBirth(studentRequest.getDateOfBirth());
        student.setAddress(studentRequest.getAddress());

        studentRespository.save(student);

        // 4. Map response thủ công (tránh lỗi ModelMapper với @MapsId)
        StudentRespone response = new StudentRespone();
        response.setStudentId(student.getStudentId());
        response.setStudentCode(student.getStudentCode());
        response.setMajor(student.getMajor());
        response.setClazz(student.getClazz());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setAddress(student.getAddress());

        return response;
    }
}
