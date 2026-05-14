package re.projecwebservice.ModelMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.respone.RoundCriteriaRespone;
import re.projecwebservice.dto.resquest.RoundCriteriaRequest;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.EvaluationCriteria;
import re.projecwebservice.entity.RoundCriteria;
import re.projecwebservice.respository.AssessmentRoundsRepository;
import re.projecwebservice.respository.EvaluationCriteriaRepository;

@Component
@RequiredArgsConstructor
public class RoundCriteriaMapper {
    private  final AssessmentRoundsRepository assessmentRoundsRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    public RoundCriteria mapRequestToEntity(RoundCriteriaRequest roundCriteriaRequest) {
        RoundCriteria roundCriteria = new RoundCriteria();
        roundCriteria.setWeight(roundCriteriaRequest.getWeight());
        AssessmentRounds assessmentRounds = assessmentRoundsRepository.findById(roundCriteriaRequest.getRound_id()).orElse(null);
        roundCriteria.setRound(assessmentRounds);
        EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(roundCriteriaRequest.getCriterion_id()).orElse(null);
        roundCriteria.setCriterion(evaluationCriteria);
        return roundCriteria;
    }
    public RoundCriteriaRespone  mapEntityToResponse(RoundCriteria  roundCriteria) {
        RoundCriteriaRespone roundCriteriaRespone = new RoundCriteriaRespone();
        roundCriteriaRespone.setWeight(roundCriteria.getWeight());
        roundCriteriaRespone.setRound_id(roundCriteria.getRound().getRoundId());
        roundCriteriaRespone.setRound_name(roundCriteria.getRound().getRoundName());
        roundCriteriaRespone.setCriterion_id(roundCriteria.getCriterion().getCriterionId());
        roundCriteriaRespone.setCriterion_name(roundCriteria.getCriterion().getCriterionName());
        return roundCriteriaRespone;
    }
}
