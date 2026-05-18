package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.Users;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    boolean existsByEmail(String email);
    Users findByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, Integer userId);
}
