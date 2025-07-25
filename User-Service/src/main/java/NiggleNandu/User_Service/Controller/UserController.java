package NiggleNandu.User_Service.Controller;

import NiggleNandu.User_Service.Clients.OrderClient;
import NiggleNandu.User_Service.Dto.OrderDto;
import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.UserEntity;
import NiggleNandu.User_Service.Entity.WishlistItem;
import NiggleNandu.User_Service.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private OrderClient orderClient;

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}/orders")
//    @PreAuthorized("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderDto>> getOrderHistory(@PathVariable Long id){
        return ResponseEntity.ok(orderClient.getUsersOrders(id));
    }

    @PostMapping("/{id}/wishlist")
    public ResponseEntity<String> addToWishlist(@PathVariable Long id, @RequestBody WishlistItem item){
        userService.addToWishlist(id, item);
        return ResponseEntity.ok("Added to wishlist");
    }

    @PostMapping("/{id}/viewed")
    public ResponseEntity<String> addViewed(@PathVariable Long id, @RequestBody RecentlyViewedProduct item){
        userService.addViewedProduct(id, item);
        return ResponseEntity.ok("Added to recently viewed");
    }
}
