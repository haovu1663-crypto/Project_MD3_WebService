package re.projecwebservice.dto.respone;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AssessmentResultsRespone {
    private  Integer resultsId;
    private Integer assignment;
    private Integer round;
    private String roundName;// name đợt đánh giá
    private Integer criterion;
    private String criterionName; // tiêu chí đánh giá : name
    private Integer evaluatedBy;   // id của giáo viên
    private String evaluatedByName;
    private BigDecimal score;
    private String comments;
    private LocalDateTime evaluationDate;
}
