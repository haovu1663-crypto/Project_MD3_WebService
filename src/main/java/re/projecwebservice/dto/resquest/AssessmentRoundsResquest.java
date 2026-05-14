package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.entity.InternshipPhases;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class AssessmentRoundsResquest {
    // đối tượng
    private Integer phase_id;
    @NotBlank
    private String roundName;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @Future
    private LocalDate endDate;
    private String description;
}
