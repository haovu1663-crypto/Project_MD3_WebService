package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.projecwebservice.dto.respone.RoundCriteriaRespone;
import re.projecwebservice.entity.RoundCriteria;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

@Repository
public interface RoundCriteriaRepository extends JpaRepository<RoundCriteria, Integer> {
    // kiểm tra xem tiêu chí và đợt đánh giá có trùng nay không
    boolean existsByRound_RoundIdAndCriterion_CriterionId(Integer roundId, Integer criterionId);
    List<RoundCriteria> findByRound_RoundId(Integer roundId);
}
