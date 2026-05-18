package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.InternshipAssignmentsMapper;
import re.projecwebservice.dto.respone.InternshipAssignmentsRespone;
import re.projecwebservice.dto.resquest.InternshipAssignmentsRequest;
import re.projecwebservice.entity.*;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.*;
import re.projecwebservice.service.intf.IinternshipAssignmentsService;
import re.projecwebservice.util.Role;
import re.projecwebservice.util.Status;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipAssignmentsService implements IinternshipAssignmentsService {
    private final InternshipAssignmentsRepository internshipAssignmentsRepository;
    private final StudentRespository studentRepository;
    private final MentorRepository mentorRepository;
    private final InternshipPhasesRespository internshipPhasesRepository;
    private final InternshipAssignmentsMapper mapper;
    private final UserRespository userRespository;

    @Override
    public InternshipAssignmentsRespone add(InternshipAssignmentsRequest request)
            throws DataConfickException, ResourceNotFoundException {

        //Kiểm tra student tồn tại
        Students student = studentRepository.findById(request.getStudentID())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy sinh viên với id: " + request.getStudentID()));

        // Kiểm tra mentor tồn tại
        Mentors mentor = mentorRepository.findById(request.getMentorID())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giáo viên hướng dẫn với id: " + request.getMentorID()));

        //Kiểm tra phase tồn tại
        InternshipPhases phase = internshipPhasesRepository.findById(request.getPhaseID())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giai đoạn thực tập với id: " + request.getPhaseID()));

        //  Kiểm tra sinh viên đã được phân công trong giai đoạn này chưa
        if (internshipAssignmentsRepository.existsByStudent_StudentIdAndPhase_PhaseId(
                request.getStudentID(), request.getPhaseID())) {
            throw new DataConfickException(
                    "Sinh viên id " + request.getStudentID()
                            + " đã được phân công trong giai đoạn id " + request.getPhaseID());
        }

        InternshipAssignments entity = mapper.mapRequestToEntity(request);
        return mapper.mapEntityToResponse(internshipAssignmentsRepository.save(entity));
    }


    @Override
    public InternshipAssignmentsRespone getById(Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users currentUser = userRespository.findByUsername(currentUsername).orElseThrow();

        InternshipAssignments entity = internshipAssignmentsRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phân công thực tập với id: " + assignmentId));

        if (currentUser.getRole() == Role.ROLE_MENTOR) {
            // MENTOR chỉ xem phân công thuộc sinh viên của mình
            if (!entity.getMentor().getMentorId().equals(currentUser.getUserId())) {
                throw new DataConfickException(
                        "MENTOR chỉ được xem phân công của sinh viên được gán cho mình");
            }
        } else if (currentUser.getRole() == Role.ROLE_STUDENT) {
            // STUDENT chỉ xem phân công của chính mình
            if (!entity.getStudent().getStudentId().equals(currentUser.getUserId())) {
                throw new DataConfickException(
                        "STUDENT chỉ được xem phân công của chính mình");
            }
        }
        return mapper.mapEntityToResponse(entity);
    }

    @Override
    public List<InternshipAssignmentsRespone> getAll() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users currentUser = userRespository.findByUsername(currentUsername).orElseThrow();

        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return internshipAssignmentsRepository.findAll()
                    .stream().map(mapper::mapEntityToResponse).toList();

        } else if (currentUser.getRole() == Role.ROLE_MENTOR) {
            return internshipAssignmentsRepository.findByMentor_MentorId(currentUser.getUserId())
                    .stream().map(mapper::mapEntityToResponse).toList();

        } else {
            return internshipAssignmentsRepository.findByStudent_StudentId(currentUser.getUserId())
                    .stream().map(mapper::mapEntityToResponse).toList();
        }
    }

    @Override
    public InternshipAssignmentsRespone updateStatus(Integer assignmentId, Status status)
            throws ResourceNotFoundException {
        InternshipAssignments entity = internshipAssignmentsRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phân công thực tập với id: " + assignmentId));
        entity.setStatus(status);
        return mapper.mapEntityToResponse(internshipAssignmentsRepository.save(entity));
    }

}
