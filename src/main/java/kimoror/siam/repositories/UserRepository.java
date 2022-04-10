package kimoror.siam.repositories;

import kimoror.siam.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    @Query(value = "SELECT u.id FROM User u WHERE u.email = :email")
    Optional<Long> getIdByEmail(@Param("email") String email);
}
