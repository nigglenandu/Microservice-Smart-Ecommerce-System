package NiggleNandu.Recommendation_Service.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("order-service")
public interface OrderClient {
    @GetMapping("/api/orders/user/{userId}")
    List<SpringDataJaxb.OrderDto> getUserOrders(@PathVariable String userId);
}
