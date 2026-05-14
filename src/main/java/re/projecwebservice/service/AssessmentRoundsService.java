package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.AssessmentRoundsMapper;
import re.projecwebservice.dto.respone.AssessmentRoundsRespone;
import re.projecwebservice.dto.resquest.AssessmentRoundsResquest;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.InternshipPhases;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.AssessmentRoundsRepository;
import re.projecwebservice.respository.InternshipPhasesRespository;
import re.projecwebservice.service.intf.IAssessmentRoundsService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentRoundsService implements IAssessmentRoundsService {
    private final AssessmentRoundsRepository assessmentRoundsRepository;
    private final AssessmentRoundsMapper  mapper;
    private final InternshipPhasesRespository  internshipPhasesRepository;
    @Override
    public AssessmentRoundsRespone add(AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException {
        InternshipPhases phase = internshipPhasesRepository.findById(request.getPhase_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giai đoạn thực tập với id: " + request.getPhase_id()));


        if (!request.getStartDate().isBefore(request.getEndDate())) {
            throw new DataConfickException("startDate phải trước endDate");
        }

        // 3. Kiểm tra startDate/endDate nằm trong phạm vi của phase
        if (request.getStartDate().isBefore(phase.getStartDate()) ||
                request.getEndDate().isAfter(phase.getEndDate())) {
            throw new DataConfickException(
                    "startDate và endDate phải nằm trong phạm vi của giai đoạn thực tập ("
                            + phase.getStartDate() + " -> " + phase.getEndDate() + ")");
        }

        AssessmentRounds round = mapper.mapRequestToEntity(request);
        round.setIsActive(true);
        return mapper.mapEntityToRespone(assessmentRoundsRepository.save(round));
    }
    @Override
    public List<AssessmentRoundsRespone> getAll() {
        return assessmentRoundsRepository.findAll()
                .stream().map(mapper::mapEntityToRespone).toList();
    }

    @Override
    public AssessmentRoundsRespone getById(Integer roundId) throws ResourceNotFoundException {
        AssessmentRounds round = assessmentRoundsRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + roundId));
        return mapper.mapEntityToRespone(round);
    }
    @Override
    public AssessmentRoundsRespone update(Integer roundId, AssessmentRoundsResquest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentRounds round = assessmentRoundsRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + roundId));

        InternshipPhases phase = internshipPhasesRepository.findById(request.getPhase_id())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy giai đoạn thực tập với id: " + request.getPhase_id()));

        if (!request.getStartDate().isBefore(request.getEndDate())) {
            throw new DataConfickException("startDate phải trước endDate");
        }
        if (request.getStartDate().isBefore(phase.getStartDate()) ||
                request.getEndDate().isAfter(phase.getEndDate())) {
            throw new DataConfickException(
                    "startDate và endDate phải nằm trong phạm vi của giai đoạn thực tập ("
                            + phase.getStartDate() + " -> " + phase.getEndDate() + ")");
        }
        round.setPhase(phase);
        round.setRoundName(request.getRoundName());
        round.setStartDate(request.getStartDate());
        round.setEndDate(request.getEndDate());
        round.setDescription(request.getDescription());
        return mapper.mapEntityToRespone(assessmentRoundsRepository.save(round));
    }
    @Override
    public AssessmentRoundsRespone delete(Integer roundId) throws ResourceNotFoundException {
        AssessmentRounds round = assessmentRoundsRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + roundId));
        assessmentRoundsRepository.delete(round);
        return mapper.mapEntityToRespone(round);
    }
}
