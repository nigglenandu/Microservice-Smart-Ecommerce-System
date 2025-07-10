package NiggleNandu.Cart_Service.Repository;

import NiggleNandu.Cart_Service.Entity.CartEntity;
import NiggleNandu.Cart_Service.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findById(String userId);
}
