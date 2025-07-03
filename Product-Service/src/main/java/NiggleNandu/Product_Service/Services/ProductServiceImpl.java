package NiggleNandu.Product_Service.Services;

import NiggleNandu.Product_Service.Dto.ProductDto;
import NiggleNandu.Product_Service.Entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepo;


    @Override
    public ProductDto createProduct(ProductDto dto) {
        ProductEntity product = new ProductEntity();
        mapDtoToEntity(dto, product);
        return mapEntityToDto(productRepo.save(product));
    }

    @Override
    public ProductDto updateProduct(ProductDto dto) {
        ProductEntity product = productRepo.findById(id)
                .orEleThrow(() -> new RuntimeException("Product not found"));
        mpaDtoToEntity(dto, product);
        return mapEntityToDto(productRepo.save(product));
    }

    @Override
    public ProductDto getProduct(Long id) {
        return null;
    }

    @Override
    public List<ProductDto> getAllActive() {
        return List.of();
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
