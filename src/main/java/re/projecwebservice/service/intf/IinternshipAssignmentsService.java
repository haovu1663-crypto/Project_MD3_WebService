package re.projecwebservice.service.intf;

import re.projecwebservice.dto.respone.InternshipAssignmentsRespone;
import re.projecwebservice.dto.resquest.InternshipAssignmentsRequest;
import re.projecwebservice.exception.DataConfickException;
import re.projecwebservice.exception.ResourceNotFoundException;
import re.projecwebservice.util.Status;

import java.util.List;

public interface IinternshipAssignmentsService {
    InternshipAssignmentsRespone add(InternshipAssignmentsRequest request)
            throws DataConfickException, ResourceNotFoundException;
    List<InternshipAssignmentsRespone> getAll() throws ResourceNotFoundException;

    InternshipAssignmentsRespone getById(Integer assignmentId)
            throws DataConfickException, ResourceNotFoundException;
    InternshipAssignmentsRespone updateStatus(Integer assignmentId, Status status)
            throws ResourceNotFoundException;
}
