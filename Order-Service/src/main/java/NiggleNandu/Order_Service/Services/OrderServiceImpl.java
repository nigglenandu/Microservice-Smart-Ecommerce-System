package NiggleNandu.Order_Service.Services;

import NiggleNandu.Order_Service.Clients.CartClient;
import NiggleNandu.Order_Service.Dtos.CartDto;
import NiggleNandu.Order_Service.Dtos.OrderDto;
import NiggleNandu.Order_Service.Dtos.OrderItemDto;
import NiggleNandu.Order_Service.Entity.OrderEntity;
import NiggleNandu.Order_Service.Entity.OrderItem;
import NiggleNandu.Order_Service.Entity.OrderStatus;
import NiggleNandu.Order_Service.Entity.PaymentStatus;
import NiggleNandu.Order_Service.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotal(cart.getTotal());

        List<OrderItem> items = cart.getItems().stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setProductId(item.getProductId());
            oi.setQuantity(item.getQuantity());
            oi.setSize(item.getSize());
            oi.setColor(item.getColor());
            oi.setPrice(item.getPrice());
            return oi;
        }).collect(Collectors.toList());

        order.setItem(items);

        OrderEntity saved = orderRepo.save(order);

        return mapToDto(saved);
    }

    @Override
    public List<OrderDto> getOrdersByUser(String userId) {
        return orderRepo.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private OrderDto mapToDto(OrderEntity order){
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus().toString());

        List<OrderItemDto> itemDtos = order.getItems().stream.map(item -> {
            OrderItemDto i = new OrderItemDto();
            i.setProductId(item.getProductId());
            i.setQuantity(item.getQuantity());
            i.setSize(item.getSize());
            i.setColor(item.getColor());
            i.setPrice(item.getprice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(itemsDtos);
        return dto;
    }
}
