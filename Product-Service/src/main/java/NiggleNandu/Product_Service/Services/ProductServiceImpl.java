package NiggleNandu.Product_Service.Services;

import NiggleNandu.Product_Service.Dto.ProductDto;
import NiggleNandu.Product_Service.Dto.ProductVariantDto;
import NiggleNandu.Product_Service.Entity.Category;
import NiggleNandu.Product_Service.Entity.ProductEntity;
import NiggleNandu.Product_Service.Entity.ProductVariant;
import NiggleNandu.Product_Service.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        ProductEntity product = new ProductEntity();
        mapDtoToEntity(dto, product);
        return mapEntityToDto(productRepo.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        ProductEntity product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        mapDtoToEntity(dto, product);
        return mapEntityToDto(productRepo.save(product));
    }

    @Override
    public ProductDto getProduct(Long id) {
        return productRepo.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllActive() {
        return productRepo.findByActiveTrue().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    private ProductDto mapEntityToDto(ProductEntity product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.isActive());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setVariants(product.getVariants().stream()
                .map(v -> {
                    ProductVariantDto variantDto = new ProductVariantDto();
                    variantDto.setSize(v.getSize());
                    variantDto.setColor(v.getColor());
                    return variantDto;
                }).collect(Collectors.toList()));
        return dto;
    }

    private void mapDtoToEntity(ProductDto dto, ProductEntity product){
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setActive(dto.isActive());
        product.setStock(dto.getStock());

        if (dto.getCategoryId() != null){
            Category category = categoryRepo.findById(dto.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        List<ProductVariant>  variants = dto.getVariants().stream().map(v -> {
            ProductVariant variant = new ProductVariant();
            variant.setColor(v.getColor());
            variant.setSize(v.getSize());
            return variant;
        }).collect(Collectors.toList());
        product.setVariants(variants);
    }
}