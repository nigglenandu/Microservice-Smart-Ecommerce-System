package NiggleNandu.Cart_Service.Controller;

import NiggleNandu.Cart_Service.Dto.CartDto;
import NiggleNandu.Cart_Service.Dto.CartRequest;
import NiggleNandu.Cart_Service.Dto.CartResponse;
import NiggleNandu.Cart_Service.Services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private ICartService cartservice;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<CartResponse> getCart(@PathVariable String userId){
        return ResponseEntity.ok(cartservice.getCart(userId));
    }

    @PostMapping("/{userId}/add")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<CartDto> addItem(@PathVariable String userId, @RequestBody CartRequest request){
        System.out.println("DEBUG: Adding to cart for user: " + userId);
        System.out.println("DEBUG: Request = " + request);
        return ResponseEntity.ok(cartservice.addToCart(userId, request));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<Void> clearCart(@PathVariable String userId){
        cartservice.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
