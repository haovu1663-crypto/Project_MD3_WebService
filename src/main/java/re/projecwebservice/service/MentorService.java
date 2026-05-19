package re.projecwebservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.MentorMapper;
import re.projecwebservice.dto.respone.MentorRepone;
import re.projecwebservice.dto.resquest.MentorRequest;
import re.projecwebservice.entity.Mentors;
import re.projecwebservice.entity.Users;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.MentorRepository;
import re.projecwebservice.respository.UserRespository;
import re.projecwebservice.service.intf.IMentorService;
import re.projecwebservice.util.Role;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MentorService implements IMentorService {
    private final MentorRepository mentorRepository;
    private final MentorMapper mapper;
    private final UserRespository userRespository;
    @Override
    public List<MentorRepone> getMentor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users user = userRespository.findByUsername(currentUsername).orElseThrow();
        if(user.getRole()== Role.ROLE_ADMIN){
            List<Mentors> mentors = mentorRepository.findAll();
            return mentors.stream().map(mapper::mapEntityToRepone).toList();
        }
        else{
          List<Mentors> mentors = mentorRepository.findMentorsByStudentId(user.getUserId());
          return mentors.stream().map(mapper::mapEntityToRepone).toList();
        }
    }

    @Override
    public MentorRepone add(MentorRequest mentorRequest) throws ResourceNotFoundException, DataConfickException {
        Integer userId = mentorRequest.getMentorId();
        Users user = userRespository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user với id: " + userId));

        //  Kiểm tra User có phải là mentor  không
        if (user.getRole() != Role.ROLE_MENTOR) {
            throw new DataConfickException(
                    "User với id " + userId + " không có role MENTOR");
        }
        if (mentorRepository.existsById(userId)) {
            throw new DataConfickException(
                    "Mentor với id " + userId + " đã tồn tại");
        }

        Mentors mentor = mapper.mapRequestToEntity(mentorRequest);
        mentor.setUser(user);
        Mentors saved = mentorRepository.save(mentor);
        return mapper.mapEntityToRepone(saved);
    }

    @Override
    public MentorRepone update(MentorRequest mentorRequest, Integer mentorId) throws ResourceNotFoundException, DataConfickException {
        // 1. Lấy thông tin người đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users currentUser = userRespository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user: " + currentUsername));

        Mentors mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy mentor với id: " + mentorId));

        //  mentor chỉ được cập nhậtthông tin của mình
        if (currentUser.getRole() == Role.ROLE_MENTOR) {
            if (!currentUser.getUserId().equals(mentorId)) {
                throw new DataConfickException(
                        "MENTOR chỉ được cập nhật thông tin của chính mình");
            }
        }
        if(mentorRepository.existsById(mentorId) && !mentorRequest.getMentorId().equals(mentorId)){
            throw new DataConfickException("ID Mentor này đã tồn tại ");
        }
        // kiêm tra xem có cần cập nhật không
        if (mentorRequest.getDepartment() != null) {
            mentor.setDepartment(mentorRequest.getDepartment());
        }
        if (mentorRequest.getAcademicRank() != null) {
            mentor.setAcademicRank(mentorRequest.getAcademicRank());
        }
        return mapper.mapEntityToRepone(mentorRepository.save(mentor));
    }

    @Override
    public MentorRepone getMentorById(Integer mentorId) throws ResourceNotFoundException, DataConfickException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users currentUser = userRespository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user: " + authentication.getName()));

        Mentors mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy mentor với id: " + mentorId));

        if (currentUser.getRole() == Role.ROLE_MENTOR) {
            if (!currentUser.getUserId().equals(mentorId)) {
                throw new DataConfickException(
                        "MENTOR chỉ được xem thông tin của chính mình");
            }
        } else if (currentUser.getRole() == Role.ROLE_STUDENT) {
            boolean Check = mentorRepository
                    .findMentorsByStudentId(currentUser.getUserId())
                    .stream().anyMatch(m -> m.getMentorId().equals(mentorId));// trả về kiêu dữ liệu true / fase
            if (!Check) {
                throw new DataConfickException(
                        "Student chỉ được xem thông tin mentor được gán cho mình");
            }
        }

        return mapper.mapEntityToRepone(mentor);
    }
}
