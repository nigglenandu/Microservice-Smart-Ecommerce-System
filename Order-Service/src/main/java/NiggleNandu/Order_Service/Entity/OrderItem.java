package NiggleNandu.Order_Service.Entity;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;
    private String productName;
    private String variant;
    private String size;
    private String color;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderItem(String color, Long id, OrderEntity order, double price, Long productId, int quantity, String productName, String size, String variant) {
        this.color = color;
        this.id = id;
        this.order = order;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.size = size;
        this.variant = variant;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public OrderItem() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
