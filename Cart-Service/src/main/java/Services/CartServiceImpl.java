package Services;

import NiggleNandu.Cart_Service.Clients.ProductClient;
import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartItemDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;
import NiggleNandu.Cart_Service.Dto.ProductDto;
import NiggleNandu.Cart_Service.Entity.CartItem;
import NiggleNandu.Cart_Service.Repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService{
    @Autowired
    private ProductClient productClient;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public CartDto addToCart(String userId, CartRequest request) {
        ProductDto product = productClient.getProduct(request.getProductId());

        if(!product.isActive()) throw new RuntimeException("Product is inactive");
        if(product.getStock() < request.getQuantity()) throw new RuntimeException("Insufficient stock");

        Cart cart = cartRepo.findById(userId).orElse(new Cart(userId));

        Optional<CartItem> existingItem = cart.getItems().stream
                .filter(item -> item.getProductId().equals(request.getProductId()) &&
                        item.getVariant().equals(request.getVariant()))
                .findFirst();

        if(existingItem.isPresent()){
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setProductId(request.getProductId());
            item.setVariant(request.getVariant());
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());
            cart.getItems().add(item);
        }

        double total = cart.getItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        cart.setTotal(total);
        cart.setDiscount(0.0);
        cart.setLastUpdated(LocalDateTime.now());

        cartRepo.save(cart);
        return null;
    }

    @Override
    public CartDto getCart(String userId) {
        return null;
    }

    @Override
    public void clearCart(String userId) {

    }
}
