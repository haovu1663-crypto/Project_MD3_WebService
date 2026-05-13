package re.projecwebservice.dto.resquest;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.util.Role;

import java.time.LocalDateTime;
@Getter
@Setter
public class Register {
    @Size(min = 6, max = 20, message = "Username phải từ 6 đến 20 ký tự")
    private String username;
    @Size(min = 5, message = "Password phải có ít nhất 5 ký tự")
    private String passwordHash;
    @Size(min = 6, max = 50, message = "FullName phải từ 6 đến 50 ký tự")
    private String fullName;
    @Email
    private String email;
    @Pattern(regexp = "^\\+?[0-9\\s-]{7,15}$")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

}
