package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.entity.EvaluationCriteria;

import java.util.List;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Integer> {
    boolean existsByCriterionName(String criterionName);
    EvaluationCriteria findByCriterionName(String criterionName);
    @Query("SELECT COUNT(rc) > 0 FROM RoundCriteria rc WHERE rc.criterion.criterionId = :criterionId")
    boolean existsRoundCriteriaByCriterionId(@Param("criterionId") Integer criterionId);

    @Query("SELECT COUNT(ar) > 0 FROM AssessmentResults ar WHERE ar.criterion.criterionId = :criterionId")
    boolean existsAssessmentResultByCriterionId(@Param("criterionId") Integer criterionId);
}
