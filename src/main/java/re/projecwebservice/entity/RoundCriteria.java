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

// tiu chí của đợt đánh giá
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoundCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoundCriterionID")
    private Integer roundCriterionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoundID", nullable = false)
    private AssessmentRounds round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CriterionID", nullable = false)
    private EvaluationCriteria criterion;

    @DecimalMin(value = "0.01", message = "Trọng số phải lớn hơn 0")
    @Column(name = "Weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
