package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.AssessmentResultsMapper;
import re.projecwebservice.dto.respone.AssessmentResultsRespone;
import re.projecwebservice.dto.resquest.AssessmentResultsRequest;
import re.projecwebservice.entity.*;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.*;
import re.projecwebservice.service.intf.IAssessmentResultsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentResultsService implements IAssessmentResultsService {
    private final AssessmentResultsRepository assessmentResultsRepository;
    private final InternshipAssignmentsRepository internshipAssignmentsRepository;
    private final AssessmentRoundsRepository assessmentRoundsRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final UserRespository userRepository;
    private final AssessmentResultsMapper mapper;

    @Override
    public AssessmentResultsRespone add(AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Users currentUser = userRepository.findByUsername(currentUsername).orElseThrow();
        //  Kiểm tra phiên phân công  tồn tại
        InternshipAssignments assignment = internshipAssignmentsRepository
                .findById(request.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy phân công thực tập với id: " + request.getAssignmentId()));

        // Kiểm tra mentor hiện tại có phải mentor được phân công không
        if (!assignment.getMentor().getMentorId().equals(currentUser.getUserId())) {
            throw new DataConfickException(
                    "Bạn không có quyền đánh giá sinh viên này, chỉ mentor được phân công mới được đánh giá");
        }

        // Kiểm tra round tồn tại
        AssessmentRounds round = assessmentRoundsRepository.findById(request.getRoundId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + request.getRoundId()));

        //  Kiểm tra criterion tồn tại
        EvaluationCriteria criterion = evaluationCriteriaRepository.findById(request.getCriterionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + request.getCriterionId()));

        // mooi sinh viên chỉ được đánh giá 1 lân trong tiêu chí
        if (assessmentResultsRepository
                .existsByAssignment_AssignmentIdAndRound_RoundIdAndCriterion_CriterionId(
                        request.getAssignmentId(), request.getRoundId(), request.getCriterionId())) {
            throw new DataConfickException(
                    "Tiêu chí id " + request.getCriterionId()
                            + " đã được đánh giá trong đợt id " + request.getRoundId()
                            + " cho phân công id " + request.getAssignmentId());
        }
        // compareto để so sánh bigdecimal 01-1
        if (request.getScore().compareTo(criterion.getMaxScore()) > 0) {
            throw new DataConfickException(
                    "Điểm " + request.getScore()
                            + " vượt quá điểm tối đa " + criterion.getMaxScore()
                            + " của tiêu chí này");
        }

        AssessmentResults entity = mapper.mapRequestToEntity(request);
        return mapper.mapEntityToResponse(assessmentResultsRepository.save(entity));
    }
    @Override
    public List<AssessmentResultsRespone> getAll(Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException {

        // Lấy user hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user: " + authentication.getName()));

        List<AssessmentResults> results;

        if (currentUser.getRole() == re.projecwebservice.util.Role.ROLE_ADMIN) {
            if(assignmentId != null) {
                // lấy theo id của bảng phân cong
                results = assessmentResultsRepository.findByAssignment_AssignmentId(assignmentId);
            }
            else {
                // lấy tất âr
                results = assessmentResultsRepository.findAll();
            }
        } else if (currentUser.getRole() == re.projecwebservice.util.Role.ROLE_MENTOR) {
            if(assignmentId != null) {
                // lấy theo id đi của phân công và id của mentor
                results = assessmentResultsRepository.findByAssignment_AssignmentIdAndEvaluatedBy_UserId(assignmentId, currentUser.getUserId());
            }
            else {
                // lấy theo id của mentor
                results = assessmentResultsRepository.findByEvaluatedBy_UserId(currentUser.getUserId());
            }

        } else {

            if(assignmentId != null) {
                // laays theo id của của student và id của bảng phân công
                results = assessmentResultsRepository.findByAssignment_AssignmentIdAndAssignment_Student_StudentId(assignmentId, currentUser.getUserId());
            }
            else {
                // lấy theo id của syuden trong bảng phân công
                results =assessmentResultsRepository.findByAssignment_Student_StudentId(currentUser.getUserId());
            }
        }
        return results.stream().map(mapper::mapEntityToResponse).toList();
    }

    @Override
    public AssessmentResultsRespone update(Integer resultId, AssessmentResultsRequest request)
            throws DataConfickException, ResourceNotFoundException {

        // 1. Lấy mentor đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy user: " + authentication.getName()));

        // 2. Tìm kết quả cần cập nhật
        AssessmentResults entity = assessmentResultsRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy kết quả đánh giá với id: " + resultId));

        // 3. Chỉ mentor tạo ra kết quả mới được cập nhật
        if (!entity.getEvaluatedBy().getUserId().equals(currentUser.getUserId())) {
            throw new DataConfickException(
                    "Bạn không có quyền cập nhật kết quả này, chỉ mentor đã tạo mới được sửa");
        }
        // 4. Kiểm tra score không vượt maxScore của tiêu chí
        if (request.getScore() != null &&
                request.getScore().compareTo(entity.getCriterion().getMaxScore()) > 0) {
            throw new DataConfickException(
                    "Điểm " + request.getScore()
                            + " vượt quá điểm tối đa " + entity.getCriterion().getMaxScore()
                            + " của tiêu chí này");
        }

        // 5. Cập nhật các field (chỉ khi có giá trị)
        if (request.getScore() != null) entity.setScore(request.getScore());
        if (request.getComments() != null) entity.setComments(request.getComments());
        if (request.getEvaluationDate() != null) entity.setEvaluationDate(request.getEvaluationDate());

        return mapper.mapEntityToResponse(assessmentResultsRepository.save(entity));
    }

}
