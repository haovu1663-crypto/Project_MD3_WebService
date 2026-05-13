package re.projecwebservice.service.intf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Repository;
import re.projecwebservice.dto.respone.MentorRepone;
import re.projecwebservice.dto.resquest.MentorRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;
@Repository
public interface IMentorService {
    List<MentorRepone> getMentor( );

    MentorRepone add(MentorRequest mentorRequest) throws ResourceNotFoundException, DataConfickException;
    MentorRepone update(MentorRequest mentorRequest, Integer mentorId)
            throws ResourceNotFoundException, DataConfickException;

    MentorRepone getMentorById(Integer mentorId)
            throws ResourceNotFoundException, DataConfickException;
}
