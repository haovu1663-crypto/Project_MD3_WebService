package re.projecwebservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// tiêu chí đanh giá
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CriterionID")
    private Integer criterionId;

    @Column(name = "CriterionName", length = 200, unique = true, nullable = false)
    private String criterionName;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @DecimalMin(value = "0.01", message = "Điểm tối đa phải lớn hơn 0")
    @Column(name = "MaxScore", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxScore;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
