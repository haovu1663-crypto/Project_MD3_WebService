package re.projecwebservice.dto.respone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorRepone {
    private Integer mentorId;
    private String mentorName;
    private String phone;
    private String email;
    private String department;
    private String academicRank;
}
