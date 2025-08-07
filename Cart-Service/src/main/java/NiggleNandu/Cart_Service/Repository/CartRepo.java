package NiggleNandu.Cart_Service.Repository;

import NiggleNandu.Cart_Service.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findById(String userId);
}
