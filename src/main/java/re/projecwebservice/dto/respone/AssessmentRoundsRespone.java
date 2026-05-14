package re.projecwebservice.dto.respone;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class AssessmentRoundsRespone {
    private Integer assess_id;
    private Integer phase_id;
    private String phaseName;
    private String roundName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

}
