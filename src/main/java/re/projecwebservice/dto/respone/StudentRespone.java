package re.projecwebservice.dto.respone;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class StudentRespone {
    private Integer studentId;
    private String studentCode;
    private String major;
    private String clazz;
    private LocalDate dateOfBirth;
    private String address;
}
