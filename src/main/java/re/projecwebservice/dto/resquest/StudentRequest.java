package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.entity.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class StudentRequest {

    private Integer studentId;

    private String studentCode;
    private String major;
    private String clazz;
    private LocalDate dateOfBirth;
    private String address;
}
