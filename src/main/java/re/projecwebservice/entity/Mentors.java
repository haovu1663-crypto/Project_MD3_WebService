package re.projecwebservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mentors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// thông tin giáo viên hướng dẫn
public class Mentors {
    @Id
    @Column(name = "mentor_id")
    private Integer mentorId;

    /**
     * Liên kết 1-1 với User (Role = MENTOR).
     * mentorId chính là userId, dùng @MapsId để chia sẻ khóa chính.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "mentor_id")
    private Users user;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "academic_rank", length = 50)
    private String academicRank;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
