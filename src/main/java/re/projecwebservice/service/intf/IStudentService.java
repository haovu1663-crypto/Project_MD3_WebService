package re.projecwebservice.service.intf;

import jakarta.servlet.http.HttpServletRequest;
import re.projecwebservice.dto.respone.StudentRespone;
import re.projecwebservice.dto.resquest.StudentRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IStudentService {
   // câu 10
    List<StudentRespone> getStudent() throws ResourceNotFoundException;
    StudentRespone getStudentById(Integer id) throws ResourceNotFoundException;
  //câu 12 add student
    StudentRespone add(StudentRequest studentRequest) throws ResourceNotFoundException, DataConfickException;
    StudentRespone update(StudentRequest studentRequest,Integer id) throws ResourceNotFoundException, DataConfickException;
}
