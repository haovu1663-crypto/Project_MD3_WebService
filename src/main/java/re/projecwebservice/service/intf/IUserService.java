package re.projecwebservice.service.intf;

import jakarta.servlet.http.HttpServletRequest;
import re.projecwebservice.dto.respone.JwtRespone;
import re.projecwebservice.dto.respone.RegisterRespone;
import re.projecwebservice.dto.respone.UserRespone;
import re.projecwebservice.dto.resquest.Login;
import re.projecwebservice.dto.resquest.Register;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.util.Role;

import java.util.List;

public interface IUserService {
    JwtRespone login(Login login);
    RegisterRespone register(Register register) throws DataConfickException;
    RegisterRespone getMe();
    List<UserRespone> getUser();
    UserRespone getUserById(Integer id) throws ResourceNotFoundException;
    UserRespone update(Register register, Integer id) throws ResourceNotFoundException, DataConfickException;
    UserRespone upadteStatus(Integer id) throws ResourceNotFoundException;
    UserRespone updateRole(Integer id, Role role) throws ResourceNotFoundException;
    UserRespone delete(Integer id) throws ResourceNotFoundException, DataConfickException;

}
