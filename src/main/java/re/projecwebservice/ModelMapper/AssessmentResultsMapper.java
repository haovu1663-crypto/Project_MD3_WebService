package re.projecwebservice.ModelMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.AssessmentResultsRespone;
import re.projecwebservice.dto.resquest.AssessmentResultsRequest;
import re.projecwebservice.entity.*;
import re.projecwebservice.respository.AssessmentRoundsRepository;
import re.projecwebservice.respository.EvaluationCriteriaRepository;
import re.projecwebservice.respository.InternshipAssignmentsRepository;
import re.projecwebservice.respository.UserRespository;

@Component
@RequiredArgsConstructor
public class AssessmentResultsMapper {
  private final AssessmentRoundsRepository assessmentRoundsRepository;
  private final EvaluationCriteriaRepository evaluationCriteriaRepository;
  private final InternshipAssignmentsRepository internshipAssignmentsRepository;
  private final UserRespository userRespository;
  public AssessmentResults mapRequestToEntity(AssessmentResultsRequest assessmentResults) {
      AssessmentResults assessmentResultsEntity = new AssessmentResults();
      assessmentResultsEntity.setScore(assessmentResults.getScore());
       assessmentResultsEntity.setComments(assessmentResults.getComments());
       assessmentResultsEntity.setEvaluationDate(assessmentResults.getEvaluationDate());
       // mentor
      Users mentor = userRespository.findById(assessmentResults.getEvaluatedById()).orElseThrow();
      assessmentResultsEntity.setEvaluatedBy(mentor);
      // đợt đánh gia s
      AssessmentRounds assessmentRounds = assessmentRoundsRepository.findById(assessmentResults.getRoundId()).orElseThrow();
      assessmentResultsEntity.setRound(assessmentRounds);
      // tiêu chí đnah giá
      EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findById(assessmentResults.getCriterionId()).orElseThrow();
      assessmentResultsEntity.setCriterion(evaluationCriteria);
      // phân cng
      InternshipAssignments internshipAssignments = internshipAssignmentsRepository.findById(assessmentResults.getAssignmentId()).orElseThrow();
      assessmentResultsEntity.setAssignment(internshipAssignments);
      return assessmentResultsEntity;
  }
  public AssessmentResultsRespone mapEntityToResponse(AssessmentResults assessmentResults) {
      AssessmentResultsRespone assessmentResultsRespone = new AssessmentResultsRespone();
      assessmentResultsRespone.setScore(assessmentResults.getScore());
      assessmentResultsRespone.setComments(assessmentResults.getComments());
      assessmentResultsRespone.setResultsId(assessmentResults.getResultId());
      assessmentResultsRespone.setEvaluationDate(assessmentResults.getEvaluationDate());
      // user
      assessmentResultsRespone.setEvaluatedBy(assessmentResults.getEvaluatedBy().getUserId());
      assessmentResultsRespone.setEvaluatedByName(assessmentResults.getEvaluatedBy().getFullName());
      //đợt đánh giá
      assessmentResultsRespone.setRound(assessmentResults.getRound().getRoundId());
      assessmentResultsRespone.setRoundName(assessmentResults.getRound().getRoundName());
      // tiêu chí
      assessmentResultsRespone.setCriterion(assessmentResults.getCriterion().getCriterionId());
      assessmentResultsRespone.setCriterionName(assessmentResults.getCriterion().getCriterionName());
      // pha công
      assessmentResultsRespone.setAssignment(assessmentResults.getAssignment().getAssignmentId());


      return assessmentResultsRespone;
  }
}
