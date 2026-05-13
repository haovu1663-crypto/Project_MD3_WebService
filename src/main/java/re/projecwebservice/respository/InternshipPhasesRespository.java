package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.InternshipPhases;
@Repository
public interface InternshipPhasesRespository extends JpaRepository<InternshipPhases,Integer >{
    boolean existsByPhaseName(String phaseName);
    InternshipPhases findByPhaseName(String phaseName);
}
