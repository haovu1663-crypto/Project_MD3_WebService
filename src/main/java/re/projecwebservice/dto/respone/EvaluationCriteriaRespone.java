package re.projecwebservice.dto.respone;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EvaluationCriteriaRespone {
    private Integer criterionId;
    private String criterionName;
    private String description;
    private BigDecimal maxScore;
}
