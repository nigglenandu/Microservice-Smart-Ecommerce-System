package NiggleNandu.Product_Service.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductVariant {
    private String size;
    private String color;

    public ProductVariant(String color, String size) {
        this.color = color;
        this.size = size;
    }

    public ProductVariant() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
