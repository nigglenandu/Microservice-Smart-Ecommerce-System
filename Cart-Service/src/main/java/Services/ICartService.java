package Services;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;

public interface ICartService {
    CartDto addToCart(String userId, CartRequest requesto);
    CartDto getCart(String userId);
    void clearCart(String userId);
}
