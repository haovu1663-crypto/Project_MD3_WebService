package re.projecwebservice.service.intf;

import re.projecwebservice.dto.respone.RoundCriteriaRespone;
import re.projecwebservice.dto.resquest.RoundCriteriaRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IRoundCriteriaService {
    RoundCriteriaRespone add(RoundCriteriaRequest request)
            throws DataConfickException, ResourceNotFoundException;
    List<RoundCriteriaRespone> getAllByRoundId(Integer roundId) throws ResourceNotFoundException;
    RoundCriteriaRespone getById(Integer roundCriterionId) throws ResourceNotFoundException;
}
