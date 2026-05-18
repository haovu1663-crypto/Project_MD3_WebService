package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class InternshipAssignmentsRequest {
    @NotNull
    private Integer studentID;
    @NotNull
    private Integer mentorID;
    @NotNull
    private Integer phaseID;
    @NotNull
    private LocalDateTime assignedDate;// ngày phân công
}
