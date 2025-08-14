package NiggleNandu.Cart_Service.Controller;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;
import NiggleNandu.Cart_Service.Dto.CartResponse;
import NiggleNandu.Cart_Service.Services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartservice;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable String userId){
        return ResponseEntity.ok(cartservice.getCart(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDto> addItem(@PathVariable String userId, @RequestBody CartRequest request){
        System.out.println("DEBUG: Adding to cart for user: " + userId);
        System.out.println("DEBUG: Request = " + request);
        return ResponseEntity.ok(cartservice.addToCart(userId, request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId){
        cartservice.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
