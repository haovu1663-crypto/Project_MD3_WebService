package re.projecwebservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Students {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    /*
     * Liên kết 1-1 với User (Role = STUDENT).
     * studentId chính là userId, dùng @MapsId để chia sẻ khóa chính.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "student_id")
    private Users user;

    @Column(name = "student_code", length = 20, unique = true, nullable = false)
    private String studentCode;

    @Column(name = "major", length = 100)
    private String major;

    @Column(name = "class", length = 50)
    private String clazz;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", length = 255)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
