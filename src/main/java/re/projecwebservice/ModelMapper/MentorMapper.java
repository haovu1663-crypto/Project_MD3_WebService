package re.projecwebservice.ModelMapper;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.MentorRepone;
import re.projecwebservice.dto.resquest.MentorRequest;
import re.projecwebservice.entity.Mentors;
import re.projecwebservice.entity.Users;
import re.projecwebservice.respository.UserRespository;

@Getter
@Setter

@Component
@RequiredArgsConstructor
public class MentorMapper {

     private final   UserRespository userRespository;
    public  Mentors mapRequestToEntity(MentorRequest mentorRequest) {
        Mentors mentors = new Mentors();

        mentors.setDepartment(mentorRequest.getDepartment());
        mentors.setAcademicRank(mentorRequest.getAcademicRank());
        return mentors;
    }
    public  MentorRepone mapEntityToRepone(Mentors mentors) {
        MentorRepone mentorRepone = new MentorRepone();
        mentorRepone.setMentorId(mentors.getMentorId());
        mentorRepone.setDepartment(mentors.getDepartment());
        mentorRepone.setAcademicRank(mentors.getAcademicRank());
        Users users = userRespository.findById(mentors.getMentorId()).get();
        mentorRepone.setMentorName(users.getFullName());
        mentorRepone.setPhone(users.getPhoneNumber());
        mentorRepone.setEmail(users.getEmail());
        return mentorRepone;
    }
}
