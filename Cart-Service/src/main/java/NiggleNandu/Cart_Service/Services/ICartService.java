package NiggleNandu.Cart_Service.Services;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;
import NiggleNandu.Cart_Service.Dto.CartResponse;

public interface ICartService {
    CartDto addToCart(String userId, CartRequest request);
    CartResponse getCart(String userId);
    void clearCart(String userId);
}
