package Services;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartItemDto;

public interface ICartService {
    CartDto addToCart(String userId, CartItemDto itemDto);
    CartDto getCart(String userId);
    void clearCart(String userId);
}
