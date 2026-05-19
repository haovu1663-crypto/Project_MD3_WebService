package re.projecwebservice.service.intf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.projecwebservice.dto.respone.AssessmentRoundsRespone;
import re.projecwebservice.dto.resquest.AssessmentRoundsResquest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IAssessmentRoundsService {
    AssessmentRoundsRespone add(AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException;

    List<AssessmentRoundsRespone> getAll();

    AssessmentRoundsRespone getById(Integer roundId) throws ResourceNotFoundException;
    AssessmentRoundsRespone update(Integer roundId, AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException;
    AssessmentRoundsRespone delete(Integer roundId) throws ResourceNotFoundException, DataConfickException;
}
