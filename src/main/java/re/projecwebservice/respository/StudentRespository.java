package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.Students;

import java.util.List;

@Repository
public interface StudentRespository extends JpaRepository<Students, Integer> {
    boolean existsByStudentCode(String studentCode);
    // lấy danh sách student dựa trên mentor
    @Query("SELECT a.student FROM InternshipAssignments a WHERE a.mentor.mentorId = :mentorId")
    List<Students> findStudentsByMentorId(@Param("mentorId") Integer mentorId);
}
