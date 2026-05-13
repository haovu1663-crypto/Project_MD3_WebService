package re.projecwebservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

//dđợt đánh gia
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentRounds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoundID")
    private Integer roundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PhaseID", nullable = false)
    private InternshipPhases phase;

    @Column(name = "RoundName", length = 100, nullable = false)
    private String roundName;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;



    @Column(name = "IsActive", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")

    private Boolean isActive ;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
