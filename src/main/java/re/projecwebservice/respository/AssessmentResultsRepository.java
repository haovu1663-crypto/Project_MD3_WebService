package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.AssessmentResults;

import java.util.List;

@Repository
public interface AssessmentResultsRepository extends JpaRepository<AssessmentResults,Integer> {
    boolean existsByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(
            Integer assignmentId, Integer roundId, Integer criterionId);
    // tìm kiếm theo phiếu pjaan công
    List<AssessmentResults> findByAssignment_AssignmentId(Integer assignmentId);

    // Lấy kết quả theo giảng viên
    List<AssessmentResults> findByEvaluatedBy_UserId(Integer userId);

    // Lấy kết quả theo id của sinh viên trong bảng phân công
    List<AssessmentResults> findByAssignment_Student_StudentId(Integer studentId);

    // Lấy kết quả theo id của bảng phân công và id của mentor
    List<AssessmentResults> findByAssignment_AssignmentIdAndEvaluatedBy_UserId(
            Integer assignmentId, Integer userId);

    // Lấy kết quả theo id phân cồn và id của student
    List<AssessmentResults> findByAssignment_AssignmentIdAndAssignment_Student_StudentId(
            Integer assignmentId, Integer studentId);
}
