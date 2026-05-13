package re.projecwebservice.dto.resquest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import re.projecwebservice.entity.Users;
@Setter
@Getter
public class MentorRequest {
    private Integer mentorId;
    private String department;
    private String academicRank;
}
