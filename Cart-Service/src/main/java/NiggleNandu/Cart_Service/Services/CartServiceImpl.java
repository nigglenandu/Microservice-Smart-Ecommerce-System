package NiggleNandu.Cart_Service.Services;

import NiggleNandu.Cart_Service.Clients.ProductClient;
import NiggleNandu.Cart_Service.Dto.*;
import NiggleNandu.Cart_Service.Entity.CartEntity;
import NiggleNandu.Cart_Service.Entity.CartItem;
import NiggleNandu.Cart_Service.Repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartDto addToCart(String userId, CartRequest request) {
        ProductDto product = productClient.getProduct(request.getProductId());

        if (!product.isActive()) throw new RuntimeException("Product is inactive");
        if (product.getStock() < request.getQuantity()) throw new RuntimeException("Insufficient stock");

        CartEntity cart = cartRepo.findById(userId)
                .orElseGet(() -> new CartEntity(userId));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()) &&
                        item.getVariant().equals(request.getVariant()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setProductId(request.getProductId());
            item.setProductName(request.getProductName());
            item.setVariant(request.getVariant());
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());
            String[] parts = request.getVariant().split(" - ");
            if(parts.length == 2){
                item.setSize(parts[0].trim());
                item.setColor(parts[1].trim());
            }
            cart.getItems().add(item);
        }

        double total = cart.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        cart.setTotal(total);
        cart.setDiscount(0.0);
        cart.setLastUpdated(LocalDateTime.now());

        CartEntity savedCart = cartRepo.save(cart);
        return convertToDto(savedCart);

    }

    @Override
    public CartResponse getCart(String userId) {
        // Always return CartResponse for API
        CartEntity cart = cartRepo.findById(userId).orElse(new CartEntity(userId));

        List<CartItemDto> items = cart.getItems().stream().map(item -> {
            CartItemDto dto = new CartItemDto();
            dto.setId(item.getId());
            dto.setProductId(item.getProductId());
            dto.setVariant(item.getVariant());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setSize(item.getSize());
            dto.setColor(item.getColor());
            return dto;
        }).collect(Collectors.toList());

        CartResponse response = new CartResponse();
        response.setCartItemList(items);
        response.setTotal(cart.getTotal());
        response.setDiscount(cart.getDiscount());
        return response;
    }

    @Override
    public void clearCart(String userId) {
        CartEntity cart = cartRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cart.setTotal(0.0);
        cart.setDiscount(0.0);
        cart.setLastUpdated(LocalDateTime.now());
        cartRepo.save(cart);
    }

    private CartDto convertToDto(CartEntity cart) {
        // Used internally for service-to-service communication
        CartDto dto = new CartDto();
        dto.setUserId(cart.getUserId());
        dto.setTotal(cart.getTotal());
        dto.setDiscount(cart.getDiscount());
        dto.setLastUpdated(cart.getLastUpdated());

        List<CartItemDto> itemDtos = cart.getItems().stream().map(item -> {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProductId());
            itemDto.setVariant(item.getVariant());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            itemDto.setSize(item.getSize());
            itemDto.setColor(item.getColor());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}
