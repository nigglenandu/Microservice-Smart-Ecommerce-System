package NiggleNandu.User_Service.Clients;

import NiggleNandu.User_Service.Dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderClientFallback implements OrderClient{
    @Override
    public List<OrderDto> getUsersOrders(Long userId) {
        return List.of();
    }
}
