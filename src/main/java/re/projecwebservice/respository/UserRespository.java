package re.projecwebservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.projecwebservice.entity.Users;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    boolean existsByEmail(String email);
    Users findByEmail(String email);
   boolean existsByUsername(String username);
   boolean existsByPhoneNumber(String phoneNumber);
   Users findByPhoneNumber(String phoneNumber);

    @Query("SELECT COUNT(s) > 0 FROM Students s WHERE s.user.userId = :userId")
    boolean existsAsStudent(@Param("userId") Integer userId);

    @Query("SELECT COUNT(m) > 0 FROM Mentors m WHERE m.user.userId = :userId")
    boolean existsAsMentor(@Param("userId") Integer userId);
}
