package NiggleNandu.Order_Service.Services;

import NiggleNandu.Order_Service.Dtos.OrderDto;

import java.util.List;

public class OrderServiceImpl implements OrderService{
    @Override
    public OrderDto placeOrder(String userId) {
        return null;
    }

    @Override
    public List<OrderDto> getOrdersByUser(String userId) {
        return List.of();
    }
}
