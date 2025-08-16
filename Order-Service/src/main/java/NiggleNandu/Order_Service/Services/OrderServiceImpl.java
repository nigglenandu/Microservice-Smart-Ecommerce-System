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

        OrderEntity order = createOrderEntity(userId, cart);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> mapToOrderItem(item, order))
                .collect(Collectors.toList());
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

    private OrderEntity createOrderEntity(String userId, CartDto cart) {
        OrderEntity order = new OrderEntity();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotal(cart.getTotal());
        order.setDiscount(cart.getDiscount() != null ? cart.getDiscount() : 0.0);
        return order;
    }

    private OrderItem mapToOrderItem(CartItemDto cartItem, OrderEntity order) {
        OrderItem oi = new OrderItem();
        oi.setProductId(cartItem.getProductId());
        oi.setProductName(cartItem.getProductName());
        oi.setVariant(cartItem.getVariant());
        oi.setQuantity(cartItem.getQuantity());
        oi.setSize(cartItem.getSize());
        oi.setColor(cartItem.getColor());
        oi.setPrice(cartItem.getPrice());
        oi.setOrder(order);
        return oi;
    }

    private OrderDto mapToDto(OrderEntity order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPaymentStatus().toString());

        List<OrderItemDto> itemDtos = order.getItem().stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        return dto;
    }

    private OrderItemDto mapToOrderItemDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setVariant(item.getVariant());
        dto.setQuantity(item.getQuantity());
        dto.setSize(item.getSize());
        dto.setColor(item.getColor());
        dto.setPrice(item.getPrice());
        return dto;
    }
}
