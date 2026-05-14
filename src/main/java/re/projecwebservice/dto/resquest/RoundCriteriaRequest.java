package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlMixed;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.EvaluationCriteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoundCriteriaRequest {
    private Integer round_id;
    private Integer criterion_id;
    @NotNull
    private BigDecimal weight;
}
