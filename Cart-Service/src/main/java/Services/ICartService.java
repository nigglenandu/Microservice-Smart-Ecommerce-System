package Services;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;

public interface ICartService {
    CartDto addToCart(String userId, CartRequest request);
    CartDto getCart(String userId);
    void clearCart(String userId);
}
