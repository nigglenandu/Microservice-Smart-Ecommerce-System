package NiggleNandu.Order_Service.Repository;

import NiggleNandu.Order_Service.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
}
