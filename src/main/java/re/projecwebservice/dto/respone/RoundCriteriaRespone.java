package re.projecwebservice.dto.respone;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RoundCriteriaRespone {
    private Integer round_id;
    private String round_name;
    private Integer criterion_id;
    private String criterion_name;
    @NotNull
    private BigDecimal weight;
}
