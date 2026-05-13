package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import re.projecwebservice.entity.Mentors;
import re.projecwebservice.entity.Students;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentors,Integer> {
    // laays danh sach mentor
    @Query("SELECT a.mentor FROM InternshipAssignments a WHERE a.student.studentId = :studentId")
    List<Mentors> findMentorsByStudentId(@Param("studentId") Integer studentId);
}
