package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.projecwebservice.entity.AssessmentRounds;

public interface AssessmentRoundsRepository extends JpaRepository<AssessmentRounds, Integer> {

    @Query("SELECT COUNT(rc) > 0 FROM RoundCriteria rc WHERE rc.round.roundId = :roundId")
    boolean existsRoundCriteriaByRoundId(@Param("roundId") Integer roundId);

    @Query("SELECT COUNT(ar) > 0 FROM AssessmentResults ar WHERE ar.round.roundId = :roundId")
    boolean existsAssessmentResultByRoundId(@Param("roundId") Integer roundId);
}
