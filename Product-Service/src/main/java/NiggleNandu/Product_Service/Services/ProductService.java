package NiggleNandu.Product_Service.Services;

import NiggleNandu.Product_Service.Dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto updateProduct(Long id, ProductDto dto);
    ProductDto getProduct(Long id);
    List<ProductDto> getAllActive();
    void deleteProduct(Long id);
}
