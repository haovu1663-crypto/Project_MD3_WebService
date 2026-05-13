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

// kết quả đánh giá
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResultID")
    private Integer resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignmentID", nullable = false)
    private InternshipAssignments assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoundID", nullable = false)
    private AssessmentRounds round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CriterionID", nullable = false)
    private EvaluationCriteria criterion;

    @DecimalMin(value = "0.0", message = "Điểm không được âm")
    @Column(name = "Score", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "Comments", columnDefinition = "TEXT")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EvaluatedBy", nullable = false)
    private Users evaluatedBy;

    @Column(name = "EvaluationDate")
    private LocalDateTime evaluationDate;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
