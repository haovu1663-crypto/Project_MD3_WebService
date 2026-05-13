package re.projecwebservice.dto.resquest;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class InternshipPhasesRequest {
    @NotBlank(message = "không được để chống ")
    private String phaseName;
    // không nằm trong quá khứ
    @FutureOrPresent
    private LocalDate startDate;
    @Future
    private LocalDate endDate;
    private String description;
}
