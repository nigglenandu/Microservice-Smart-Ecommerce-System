package NiggleNandu.Order_Service.Services;

import NiggleNandu.Order_Service.Clients.CartClient;
import NiggleNandu.Order_Service.Dtos.CartDto;
import NiggleNandu.Order_Service.Dtos.OrderDto;
import NiggleNandu.Order_Service.Entity.OrderEntity;
import NiggleNandu.Order_Service.Entity.OrderStatus;
import NiggleNandu.Order_Service.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartClient cartClient;

    @Override
    public OrderDto placeOrder(String userId) {
        CartDto cart = cartClient.getCart(userId);

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentStatus("PENDING");
        order.setTotal(cart.getTotal());

        return null;
    }

    @Override
    public List<OrderDto> getOrdersByUser(String userId) {
        return List.of();
    }
}
