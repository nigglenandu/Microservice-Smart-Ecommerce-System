package NiggleNandu.Order_Service.Services;

import NiggleNandu.Order_Service.Dtos.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(String userId);
    List<OrderDto> getOrdersByUser(String userId);
}
