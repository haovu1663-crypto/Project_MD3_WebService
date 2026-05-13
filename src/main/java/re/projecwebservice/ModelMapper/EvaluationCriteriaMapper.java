package re.projecwebservice.ModelMapper;

import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.resquest.EvaluationCriteriaRequest;
import re.projecwebservice.entity.EvaluationCriteria;
@Component
public class EvaluationCriteriaMapper {
    public EvaluationCriteria mapRequestToEntity(EvaluationCriteriaRequest request) {
        EvaluationCriteria criteria = new EvaluationCriteria();
        criteria.setCriterionName(request.getCriterionName());
        criteria.setDescription(request.getDescription());
        criteria.setMaxScore(request.getMaxScore());
        return criteria;
    }

    public EvaluationCriteriaRespone mapEntityToRespone(EvaluationCriteria criteria) {
        EvaluationCriteriaRespone respone = new EvaluationCriteriaRespone();
        respone.setCriterionId(criteria.getCriterionId());
        respone.setCriterionName(criteria.getCriterionName());
        respone.setDescription(criteria.getDescription());
        respone.setMaxScore(criteria.getMaxScore());
        return respone;
    }
}
