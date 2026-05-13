package re.projecwebservice.dto.respone;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import re.projecwebservice.util.Role;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRespone {
    private Integer userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
