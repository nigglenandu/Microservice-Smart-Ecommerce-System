package NiggleNandu.User_Service.Clients;

import NiggleNandu.User_Service.Dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service", path = "/api/orders", fallback = OrderClientFallback.class)
public interface OrderClient {

    @GetMapping("/api/orders/user/{userId}")
    List<OrderDto> getUsersOrders(@PathVariable Long userId);
}
