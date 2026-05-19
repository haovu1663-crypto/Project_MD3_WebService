package re.projecwebservice.service.intf;

import re.projecwebservice.dto.respone.InternshipPhasesRespone;
import re.projecwebservice.dto.resquest.InternshipPhasesRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;

import java.util.List;

public interface IInternshipPhasesService {
    List<InternshipPhasesRespone> getAll();
    InternshipPhasesRespone add(InternshipPhasesRequest request)
            throws DataConfickException, ResourceNotFoundException;
    InternshipPhasesRespone getById(Integer phaseId) throws ResourceNotFoundException;
    InternshipPhasesRespone update(Integer phaseId, InternshipPhasesRequest request)
            throws DataConfickException, ResourceNotFoundException;
    InternshipPhasesRespone delete(Integer phaseId) throws ResourceNotFoundException, DataConfickException;
}
