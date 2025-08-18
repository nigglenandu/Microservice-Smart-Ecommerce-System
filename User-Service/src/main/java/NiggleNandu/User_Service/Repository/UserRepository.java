package NiggleNandu.User_Service.Repository;

import NiggleNandu.User_Service.Entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUserEntity, Long> {
}
