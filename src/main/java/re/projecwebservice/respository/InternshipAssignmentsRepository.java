package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import re.projecwebservice.entity.InternshipAssignments;

import java.util.List;

public interface InternshipAssignmentsRepository extends JpaRepository<InternshipAssignments,Integer> {
    boolean existsByStudent_StudentIdAndPhase_PhaseId(Integer studentId, Integer phaseId);
    List<InternshipAssignments> findByStudent_StudentId(Integer studentId);
    List<InternshipAssignments> findByMentor_MentorId(Integer mentorId);
}
