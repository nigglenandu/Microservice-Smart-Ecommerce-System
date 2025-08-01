package NiggleNandu.Recommendation_Service.Clients;

import NiggleNandu.Recommendation_Service.Dtos.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("order-service")
public interface OrderClient {
    @GetMapping("/api/orders/user/{userId}")
    List<OrderDto> getUserOrders(@PathVariable String userId);
}
