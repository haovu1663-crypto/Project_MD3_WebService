package re.projecwebservice.ModelMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.InternshipAssignmentsRespone;
import re.projecwebservice.dto.resquest.InternshipAssignmentsRequest;
import re.projecwebservice.entity.*;
import re.projecwebservice.respository.InternshipPhasesRespository;
import re.projecwebservice.respository.MentorRepository;
import re.projecwebservice.respository.StudentRespository;
import re.projecwebservice.respository.UserRespository;

@Component
@RequiredArgsConstructor
public class InternshipAssignmentsMapper {
    private final StudentRespository studentRespository;
    private final MentorRepository mentorRepository;

    private final InternshipPhasesRespository internshipPhasesRespository;
    private final UserRespository userRespository;
    public InternshipAssignments mapRequestToEntity(InternshipAssignmentsRequest internshipAssignmentsRequest) {
        InternshipAssignments internshipAssignments = new InternshipAssignments();
        internshipAssignments.setAssignedDate(internshipAssignmentsRequest.getAssignedDate());
        // biên đổi student
        Students  students = studentRespository.findById(internshipAssignmentsRequest.getStudentID()).orElseThrow();
        internshipAssignments.setStudent(students);
        Mentors mentors = mentorRepository.findById(internshipAssignmentsRequest.getMentorID()).orElseThrow();
        internshipAssignments.setMentor(mentors);
        InternshipPhases internshipPhases = internshipPhasesRespository.findById(internshipAssignmentsRequest.getPhaseID()).orElse(null);
        internshipAssignments.setPhase(internshipPhases);
        return internshipAssignments;
    }
    public InternshipAssignmentsRespone mapEntityToResponse(InternshipAssignments internshipAssignments) {
        InternshipAssignmentsRespone internshipAssignmentsRespone = new InternshipAssignmentsRespone();
        internshipAssignmentsRespone.setAssignmentId(internshipAssignments.getAssignmentId());
        internshipAssignmentsRespone.setAssignedDate(internshipAssignments.getAssignedDate());
        internshipAssignmentsRespone.setStudentId(internshipAssignments.getStudent().getStudentId());
        Users users = userRespository.findById(internshipAssignments.getStudent().getStudentId()).orElseThrow();
        internshipAssignmentsRespone.setStudentName(users.getFullName());
        Users mentor = userRespository.findById(internshipAssignments.getMentor().getMentorId()).orElseThrow();
        internshipAssignmentsRespone.setMentorName(mentor.getFullName());
        internshipAssignmentsRespone.setMentorId(mentor.getUserId());
        internshipAssignmentsRespone.setStatus(internshipAssignments.getStatus());
        internshipAssignmentsRespone.setPhaseId(internshipAssignments.getPhase().getPhaseId());
        internshipAssignmentsRespone.setPhaseName(internshipAssignments.getPhase().getPhaseName());
        return internshipAssignmentsRespone;
    }
}
