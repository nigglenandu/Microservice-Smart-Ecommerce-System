package NiggleNandu.Product_Service.Dto;

import java.util.List;


import NiggleNandu.Product_Service.Entity.Category;
import NiggleNandu.Product_Service.Entity.ProductVariant;
import jakarta.persistence.*;

import java.util.List;

public class ProductDto {
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean active;

    @ElementCollection
    private List<ProductVariant> variants;

    @ManyToOne
    private Category category;

    private int stock;
}
