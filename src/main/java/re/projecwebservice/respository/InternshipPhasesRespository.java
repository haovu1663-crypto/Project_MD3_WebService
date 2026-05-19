package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.InternshipPhases;
@Repository
public interface InternshipPhasesRespository extends JpaRepository<InternshipPhases,Integer >{
    boolean existsByPhaseName(String phaseName);
    InternshipPhases findByPhaseName(String phaseName);


    @Query("SELECT COUNT(a) > 0 FROM AssessmentRounds a WHERE a.phase.phaseId = :phaseId")
    boolean existsAssessmentRoundByPhaseId(@Param("phaseId") Integer phaseId);

    @Query("SELECT COUNT(ia) > 0 FROM InternshipAssignments ia WHERE ia.phase.phaseId = :phaseId")
    boolean existsInternshipAssignmentByPhaseId(@Param("phaseId") Integer phaseId);
}
