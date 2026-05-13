package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.entity.EvaluationCriteria;

import java.util.List;

public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Integer> {
    boolean existsByCriterionName(String criterionName);
    EvaluationCriteria findByCriterionName(String criterionName);
}
