package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.projecwebservice.ModelMapper.EvaluationCriteriaMapper;
import re.projecwebservice.dto.respone.EvaluationCriteriaRespone;
import re.projecwebservice.dto.resquest.EvaluationCriteriaRequest;
import re.projecwebservice.entity.EvaluationCriteria;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.EvaluationCriteriaRepository;
import re.projecwebservice.service.intf.IEvaluationCriteriaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationCriteriaService implements IEvaluationCriteriaService {
    private final EvaluationCriteriaRepository evaluationCriteriaRepository;
    private final EvaluationCriteriaMapper mapper;
    @Override
    public EvaluationCriteriaRespone add(EvaluationCriteriaRequest request) throws DataConfickException {
        // Kiểm tra tên tiêu chí đã tồn tại chưa
        if (evaluationCriteriaRepository.existsByCriterionName(request.getCriterionName())) {
            throw new DataConfickException(
                    "Tiêu chí đánh giá với tên '" + request.getCriterionName() + "' đã tồn tại");
        }
        EvaluationCriteria criteria = mapper.mapRequestToEntity(request);
        return mapper.mapEntityToRespone(evaluationCriteriaRepository.save(criteria));
    }
    @Override
    public List<EvaluationCriteriaRespone> getAll() {
        return evaluationCriteriaRepository.findAll()
                .stream().map(mapper::mapEntityToRespone).toList();
    }
    @Override
    public EvaluationCriteriaRespone getById(Integer criterionId) throws ResourceNotFoundException {
        EvaluationCriteria criteria = evaluationCriteriaRepository.findById(criterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + criterionId));
        return mapper.mapEntityToRespone(criteria);
    }
    @Override
    public EvaluationCriteriaRespone update(Integer criterionId, EvaluationCriteriaRequest request)
            throws DataConfickException, ResourceNotFoundException {
        EvaluationCriteria criteria = evaluationCriteriaRepository.findById(criterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + criterionId));
        // kiêm tra trung database
          EvaluationCriteria evaluationCriteria = evaluationCriteriaRepository.findByCriterionName(request.getCriterionName());
        if (evaluationCriteria != null && !criterionId.equals( evaluationCriteria.getCriterionId())) {
            throw new DataConfickException(
                    "Tiêu chí đánh giá với tên '" + request.getCriterionName() + "' đã tồn tại");
        }

        criteria.setCriterionName(request.getCriterionName());
        criteria.setDescription(request.getDescription());
        criteria.setMaxScore(request.getMaxScore());

        return mapper.mapEntityToRespone(evaluationCriteriaRepository.save(criteria));
    }

    @Override
    public EvaluationCriteriaRespone delete(Integer criterionId) throws DataConfickException, ResourceNotFoundException {
        EvaluationCriteria criteria = evaluationCriteriaRepository.findById(criterionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy tiêu chí đánh giá với id: " + criterionId));
        evaluationCriteriaRepository.delete(criteria);
        return mapper.mapEntityToRespone(criteria);
    }
}
