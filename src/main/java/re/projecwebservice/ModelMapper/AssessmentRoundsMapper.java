package re.projecwebservice.ModelMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.AssessmentRoundsRespone;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.resquest.AssessmentRoundsResquest;
import re.projecwebservice.dto.resquest.EvaluationCriteriaRequest;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.EvaluationCriteria;
import re.projecwebservice.entity.InternshipPhases;
import re.projecwebservice.respository.InternshipPhasesRespository;

@Component
@RequiredArgsConstructor
public class AssessmentRoundsMapper {
    private final InternshipPhasesRespository internshipPhasesRespository;
    public AssessmentRounds mapRequestToEntity(AssessmentRoundsResquest request) {
      AssessmentRounds entity = new AssessmentRounds();
        entity.setRoundName(request.getRoundName());
        entity.setDescription(request.getDescription());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        InternshipPhases internshipPhases = internshipPhasesRespository.findById(request.getPhase_id()).orElse(null);
        entity.setPhase(internshipPhases);
        return entity;
    }

    public AssessmentRoundsRespone mapEntityToRespone(AssessmentRounds entity) {
        AssessmentRoundsRespone respone = new AssessmentRoundsRespone();
        respone.setAssess_id(entity.getRoundId());
        respone.setRoundName(entity.getRoundName());
        respone.setDescription(entity.getDescription());
        respone.setStartDate(entity.getStartDate());
        respone.setEndDate(entity.getEndDate());
        respone.setPhase_id(entity.getPhase().getPhaseId());
        respone.setPhaseName(entity.getPhase().getPhaseName());
        return respone;
    }
}
