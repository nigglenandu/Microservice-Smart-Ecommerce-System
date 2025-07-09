package Services;

import NiggleNandu.Cart_Service.Dto.CartRequest;
import NiggleNandu.Cart_Service.Dto.CartResponse;

public interface ICartService {
    void addToCart(String userId, CartRequest request);
    CartResponse getCart(String userId);
    void clearCart(String userId, Long itemId);
}
