package re.projecwebservice.ModelMapper;

import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.InternshipPhasesRespone;
import re.projecwebservice.dto.resquest.InternshipPhasesRequest;
import re.projecwebservice.entity.InternshipPhases;

@Component
public class InternshipPhasesMapper {
    public InternshipPhases mapRequestToEntity(InternshipPhasesRequest request) {
        InternshipPhases phase = new InternshipPhases();
        phase.setPhaseName(request.getPhaseName());
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        phase.setDescription(request.getDescription());
        return phase;
    }

    public InternshipPhasesRespone mapEntityToRespone(InternshipPhases phase) {
        InternshipPhasesRespone respone = new InternshipPhasesRespone();
        respone.setPhaseId(phase.getPhaseId());
        respone.setPhaseName(phase.getPhaseName());
        respone.setStartDate(phase.getStartDate());
        respone.setEndDate(phase.getEndDate());
        respone.setDescription(phase.getDescription());
        return respone;
    }
}
