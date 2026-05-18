package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.EvaluationCriteria;
import re.projecwebservice.entity.InternshipAssignments;
import re.projecwebservice.entity.Users;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class AssessmentResultsRequest {
    @NotNull
    private Integer assignmentId;
    @NotNull
    private Integer roundId;
    @NotNull
    private Integer criterionId;
    @NotNull
    private Integer evaluatedById;   // id của giáo viên
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal score;
    private String comments;
    @NotNull
    private LocalDateTime evaluationDate;
}
