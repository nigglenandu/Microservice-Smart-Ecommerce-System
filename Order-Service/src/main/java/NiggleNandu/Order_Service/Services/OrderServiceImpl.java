package NiggleNandu.Order_Service.Services;

import NiggleNandu.Order_Service.Clients.CartClient;
import NiggleNandu.Order_Service.Dtos.CartDto;
import NiggleNandu.Order_Service.Dtos.CartItemDto;
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
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartClient cartClient;

    @Override
    public OrderDto placeOrder(String userId) {
        CartDto cart = cartClient.getCart(userId);

        List<CartItemDto> cartItems = cart.getCartItemList();
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotal(cart.getTotal());
        order.setDiscount(cart.getDiscount() != null ? cart.getDiscount() : 0.0);

        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setProductId(item.getProductId());
            oi.setProductName(item.getProductName());
            oi.setVariant(item.getVariant());
            oi.setQuantity(item.getQuantity());
            oi.setSize(item.getSize());
            oi.setColor(item.getColor());
            oi.setPrice(item.getPrice());
            oi.setOrder(order);
            return oi;
        }).collect(Collectors.toList());

        order.setItem(orderItems);

        OrderEntity savedOrder = orderRepo.save(order);

        return mapToDto(savedOrder);
    }

    @Override
    public List<OrderDto> getOrdersByUser(String userId) {
        return orderRepo.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private OrderDto mapToDto(OrderEntity order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus().toString());

        List<OrderItemDto> itemDtos = order.getItem().stream().map(item -> {
            OrderItemDto i = new OrderItemDto();
            i.setProductId(item.getProductId());
            i.setQuantity(item.getQuantity());
            i.setSize(item.getSize());
            i.setColor(item.getColor());
            i.setPrice(item.getPrice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}
