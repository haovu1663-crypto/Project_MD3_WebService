package re.projecwebservice.ModelMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.StudentRespone;
import re.projecwebservice.dto.resquest.StudentRequest;
import re.projecwebservice.entity.Students;
import re.projecwebservice.entity.Users;
import re.projecwebservice.respository.UserRespository;

@Component
@RequiredArgsConstructor
public class StudentMapper {
    final UserRespository userRespository;
    public Students mapRequestToEntity(StudentRequest studentRequest) {
      Students students = new Students();
      Users users = userRespository.findById(studentRequest.getStudentId()).orElse(null);
      students.setUser(users);
      students.setStudentCode(studentRequest.getStudentCode());
      students.setMajor(studentRequest.getMajor());
      students.setClazz(studentRequest.getClazz());
      students.setDateOfBirth(studentRequest.getDateOfBirth());
      students.setAddress(studentRequest.getAddress());
      return students;
    }
    public StudentRespone mapEntityToResponse(Students students) {
        StudentRespone studentRespone = new StudentRespone();
        studentRespone.setStudentId(students.getStudentId());
        studentRespone.setStudentCode(students.getStudentCode());
        studentRespone.setMajor(students.getMajor());
        studentRespone.setClazz(students.getClazz());
        studentRespone.setDateOfBirth(students.getDateOfBirth());
        studentRespone.setAddress(students.getAddress());
        studentRespone.setFullName(students.getUser().getFullName());
        studentRespone.setStudentId(students.getStudentId());
        return studentRespone;
    }
}
