package NiggleNandu.Product_Service.Clients;

import NiggleNandu.Product_Service.Dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClients {
    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable Long id);
}
