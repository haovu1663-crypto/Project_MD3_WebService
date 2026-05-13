package re.projecwebservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.util.Status;

import java.time.LocalDateTime;

// phân công thực tập
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Integer assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID", nullable = false)
    private Students student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MentorID", nullable = false)
    private Mentors mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PhaseID", nullable = false)
    private InternshipPhases phase;

    @Column(name = "AssignedDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assignedDate;



    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)

    private Status status = Status.PENDING;

    @CreationTimestamp
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;


}
