package NiggleNandu.Order_Service.Repository;

import NiggleNandu.Order_Service.Dtos.OrderDto;
import NiggleNandu.Order_Service.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByUserId(String userId);
}
