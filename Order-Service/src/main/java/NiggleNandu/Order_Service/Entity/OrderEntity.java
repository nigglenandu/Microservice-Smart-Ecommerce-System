package NiggleNandu.Order_Service.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private double total;
    private double discount;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> item;

    private String shippingAddress;

    public OrderEntity(double discount, Long id, List<OrderItem> item, LocalDateTime orderDate, String shippingAddress, OrderStatus status, double total, String userId) {
        this.discount = discount;
        this.id = id;
        this.item = item;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.total = total;
        this.userId = userId;
    }

    public OrderEntity() {
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getItem() {
        return item;
    }

    public void setItem(List<OrderItem> item) {
        this.item = item;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
