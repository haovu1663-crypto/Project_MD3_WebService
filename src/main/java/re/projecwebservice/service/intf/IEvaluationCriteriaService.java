package re.projecwebservice.service.intf;

import org.springframework.stereotype.Repository;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.resquest.EvaluationCriteriaRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

@Repository
public interface IEvaluationCriteriaService {
    EvaluationCriteriaRespone add(EvaluationCriteriaRequest request) throws DataConfickException;
    List<EvaluationCriteriaRespone> getAll();
    EvaluationCriteriaRespone getById(Integer criterionId) throws ResourceNotFoundException;
    EvaluationCriteriaRespone update(Integer criterionId, EvaluationCriteriaRequest request) throws DataConfickException, ResourceNotFoundException;
    EvaluationCriteriaRespone delete(Integer criterionId) throws DataConfickException, ResourceNotFoundException;
}
