package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    RoundCriteria findByRound_RoundIdAndCriterion_CriterionId(Integer roundId, Integer criterionId);

    // Kiểm tra AssessmentResult đã tồn tại với RoundCriteriaID chưa
    @Query("SELECT COUNT(ar) > 0 FROM AssessmentResults ar " +
            "JOIN RoundCriteria rc ON rc.round = ar.round AND rc.criterion = ar.criterion " +
            "WHERE rc.roundCriterionId = :roundCriterionId")
    boolean existsByRoundCriteriaId(@Param("roundCriterionId") Integer roundCriterionId);
}
