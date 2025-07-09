package NiggleNandu.Cart_Service.Controller;

import Services.ICartService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private ICartService cartservice;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        return ResponseEntity.ok(cartservice.getCart(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDto> addItem(@PathVariable String userId, @RequestBody CartItemDto itemDto){
        return ResponseEntity.ok(cartservice.addCart(userId, itemDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId){
        cartservice.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
