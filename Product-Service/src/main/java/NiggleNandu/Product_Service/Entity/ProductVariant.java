package NiggleNandu.Product_Service.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductVariant {
    private String size;
    private String color;
}
