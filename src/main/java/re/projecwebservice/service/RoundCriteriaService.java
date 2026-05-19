package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.RoundCriteriaMapper;
import re.projecwebservice.dto.respone.RoundCriteriaRespone;
import re.projecwebservice.dto.resquest.RoundCriteriaRequest;
import re.projecwebservice.entity.AssessmentRounds;
import re.projecwebservice.entity.EvaluationCriteria;
import re.projecwebservice.entity.RoundCriteria;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.AssessmentRoundsRepository;
import re.projecwebservice.respository.EvaluationCriteriaRepository;
import re.projecwebservice.respository.RoundCriteriaRepository;
import re.projecwebservice.service.intf.IRoundCriteriaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundCriteriaService implements IRoundCriteriaService {
    private final RoundCriteriaRepository roundCriteriaRepository;
    private final AssessmentRoundsRepository assessmentRoundsRepository;
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final RoundCriteriaMapper mapper;

    @Override
    public RoundCriteriaRespone add(RoundCriteriaRequest request)
            throws DataConfickException, ResourceNotFoundException {
        AssessmentRounds round = assessmentRoundsRepository.findById(request.getRound_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + request.getRound_id()));
        EvaluationCriteria criterion = evaluationCriteriaRepository.findById(request.getCriterion_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + request.getCriterion_id()));
        //kiểm tra đợt đánh giá và tiêu chí đánh giá đã tồn tại hay chưa
        if (roundCriteriaRepository.existsByRound_RoundIdAndCriterion_CriterionId(
                request.getRound_id(), request.getCriterion_id())) {
            throw new DataConfickException(
                    "Tiêu chí id " + request.getCriterion_id()
                            + " đã tồn tại trong đợt đánh giá id " + request.getRound_id());
        }
        RoundCriteria roundCriteria = mapper.mapRequestToEntity(request);
        return mapper.mapEntityToResponse(roundCriteriaRepository.save(roundCriteria));
    }
    @Override
    public List<RoundCriteriaRespone> getAllByRoundId(Integer roundId) throws ResourceNotFoundException {
        // Kiểm tra round tồn tại
        if (!assessmentRoundsRepository.existsById(roundId)) {
            throw new ResourceNotFoundException(
                    "Không tìm thấy đợt đánh giá với id: " + roundId);
        }
        return roundCriteriaRepository.findByRound_RoundId(roundId)
                .stream().map(mapper::mapEntityToResponse).toList();
    }

    @Override
    public RoundCriteriaRespone getById(Integer roundCriterionId) throws ResourceNotFoundException {
        RoundCriteria roundCriteria = roundCriteriaRepository.findById(roundCriterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đợt đánh giá với id: " + roundCriterionId));
        return mapper.mapEntityToResponse(roundCriteria);
    }

    @Override
    public RoundCriteriaRespone update(Integer roundCriterionId, RoundCriteriaRequest request)
            throws ResourceNotFoundException, DataConfickException {
        RoundCriteria roundCriteria = roundCriteriaRepository.findById(roundCriterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí của đợt đánh giá: " + roundCriterionId));

        AssessmentRounds round = assessmentRoundsRepository.findById(request.getRound_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy đợt đánh giá với id: " + request.getRound_id()));

        EvaluationCriteria criterion = evaluationCriteriaRepository.findById(request.getCriterion_id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + request.getCriterion_id()));
        RoundCriteria roundCriteriaCheck = roundCriteriaRepository
                .findByRound_RoundIdAndCriterion_CriterionId(
                        request.getRound_id(), request.getCriterion_id());
        if (roundCriteriaCheck != null
                && !roundCriteriaCheck.getRoundCriterionId().equals(roundCriterionId)) {
            throw new DataConfickException(
                    "Tiêu chí id " + request.getCriterion_id()
                            + " đã tồn tại trong đợt đánh giá id " + request.getRound_id());
        }
        roundCriteria.setRound(round);
        roundCriteria.setCriterion(criterion);
        roundCriteria.setWeight(request.getWeight());

        return mapper.mapEntityToResponse(roundCriteriaRepository.save(roundCriteria));
    }
    @Override
    public RoundCriteriaRespone delete(Integer roundCriterionId) throws ResourceNotFoundException, DataConfickException {
        RoundCriteria roundCriteria = roundCriteriaRepository.findById(roundCriterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đợt đánh giá với id: " + roundCriterionId));
        if(roundCriteriaRepository.existsByRoundCriteriaId(roundCriterionId)) {
            throw new DataConfickException("tiêu chí đánh giá này đ tồn tại trong kết quả đánh giá ");
        }
        roundCriteriaRepository.delete(roundCriteria);
        return mapper.mapEntityToResponse(roundCriteria);
    }
}
