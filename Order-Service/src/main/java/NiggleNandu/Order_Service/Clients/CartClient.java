package NiggleNandu.Order_Service.Clients;

import NiggleNandu.Order_Service.Dtos.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "cart-service",  url = "http://localhost:8088")
public interface CartClient {
    @GetMapping("api/carts/{userId}")
    CartDto getCart(@PathVariable String userId);
}
