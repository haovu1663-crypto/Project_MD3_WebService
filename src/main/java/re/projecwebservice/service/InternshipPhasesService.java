package re.projecwebservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import re.projecwebservice.ModelMapper.InternshipPhasesMapper;
import re.projecwebservice.dto.respone.InternshipPhasesRespone;
import re.projecwebservice.dto.resquest.InternshipPhasesRequest;
import re.projecwebservice.entity.InternshipPhases;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.respository.InternshipPhasesRespository;
import re.projecwebservice.service.intf.IInternshipPhasesService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPhasesService implements IInternshipPhasesService {
    private final InternshipPhasesRespository internshipPhasesRepository;
    private final InternshipPhasesMapper mapper;
    @Override
    public InternshipPhasesRespone add(InternshipPhasesRequest request) throws DataConfickException, ResourceNotFoundException {
        if (internshipPhasesRepository.existsByPhaseName(request.getPhaseName())) {
            throw new DataConfickException(
                    "Giai đoạn thực tập với tên '" + request.getPhaseName() + "' đã tồn tại");
        }
        // kiển tra ngày tháng
        if (!request.getStartDate().isBefore(request.getEndDate())) {
            throw new DataConfickException(
                    "startDate phải trước endDate");
        }
        InternshipPhases phase = mapper.mapRequestToEntity(request);
        return mapper.mapEntityToRespone(internshipPhasesRepository.save(phase));
    }
    @Override
    public List<InternshipPhasesRespone> getAll() {
        return internshipPhasesRepository.findAll()
                .stream().map(mapper::mapEntityToRespone).toList();
    }

    @Override
    public InternshipPhasesRespone getById(Integer phaseId) throws ResourceNotFoundException {
        InternshipPhases phase = internshipPhasesRepository.findById(phaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giai đoạn thực tập với id: " + phaseId));
        return mapper.mapEntityToRespone(phase);
    }
    @Override
    public InternshipPhasesRespone update(Integer phaseId, InternshipPhasesRequest request)
            throws DataConfickException, ResourceNotFoundException {
        InternshipPhases phase = internshipPhasesRepository.findById(phaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giai đoạn thực tập với id: " + phaseId));
        InternshipPhases internshipPhases = internshipPhasesRepository.findByPhaseName(request.getPhaseName());
        if (internshipPhases != null&& !phaseId.equals(internshipPhases.getPhaseId())) {
            throw new DataConfickException(
                    "Giai đoạn thực tập với tên '" + request.getPhaseName() + "' đã tồn tại");
        }

        if (!request.getStartDate().isBefore(request.getEndDate())) {
            throw new DataConfickException("startDate phải trước endDate");
        }
        phase.setPhaseName(request.getPhaseName());
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        phase.setDescription(request.getDescription());
        return mapper.mapEntityToRespone(internshipPhasesRepository.save(phase));
    }
    @Override
    public InternshipPhasesRespone delete(Integer phaseId) throws ResourceNotFoundException {
        InternshipPhases phase = internshipPhasesRepository.findById(phaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giai đoạn thực tập với id: " + phaseId));
        internshipPhasesRepository.delete(phase);
        return mapper.mapEntityToRespone(phase);
    }

}
