package kimoror.siam.repositories.jpa;

import kimoror.siam.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
