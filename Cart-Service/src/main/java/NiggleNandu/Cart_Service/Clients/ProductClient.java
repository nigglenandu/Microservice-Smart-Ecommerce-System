package NiggleNandu.Cart_Service.Clients;

import NiggleNandu.Cart_Service.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDto getProduct(@PathVariable("id") Long id);
}
