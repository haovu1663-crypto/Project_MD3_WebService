package re.projecwebservice.service.intf;

import re.projecwebservice.dto.respone.AssessmentResultsRespone;
import re.projecwebservice.dto.resquest.AssessmentResultsRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IAssessmentResultsService {
    AssessmentResultsRespone add(AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException;
    List<AssessmentResultsRespone> getAll(Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException;

    AssessmentResultsRespone update(Integer resultId, AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException;
}
