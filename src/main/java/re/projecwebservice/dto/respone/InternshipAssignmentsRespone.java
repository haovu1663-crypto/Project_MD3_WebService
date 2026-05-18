package re.projecwebservice.dto.respone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.entity.InternshipPhases;
import re.projecwebservice.entity.Mentors;
import re.projecwebservice.entity.Students;
import re.projecwebservice.util.Status;

import java.time.LocalDateTime;

@Getter
@Setter
public class InternshipAssignmentsRespone {
    private Integer assignmentId;
    private Integer studentId;
    private String studentName;
    private Integer mentorId;
    private String mentorName;
    private Integer phaseId;
    private String phaseName;
    private LocalDateTime assignedDate;
    private Status status;
}
