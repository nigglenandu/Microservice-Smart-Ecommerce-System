package NiggleNandu.User_Service.Controller;

import NiggleNandu.User_Service.Clients.OrderClient;
import NiggleNandu.User_Service.Clients.SecurityClient;
import NiggleNandu.User_Service.Dto.OrderDto;
import NiggleNandu.User_Service.Dto.SignupRequestDto;
import NiggleNandu.User_Service.Dto.SignupRequestForSecurity;
import NiggleNandu.User_Service.Entity.Address;
import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.AppUserEntity;
import NiggleNandu.User_Service.Entity.WishlistItem;
import NiggleNandu.User_Service.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    private final OrderClient orderClient;
    private final SecurityClient securityClient;

    public UserController(OrderClient orderClient, IUserService userService, SecurityClient securityClient) {
        this.orderClient = orderClient;
        this.userService = userService;
        this.securityClient = securityClient;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<AppUserEntity> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserEntity> registerUser(@RequestBody SignupRequestDto signupRequest) {

        SignupRequestForSecurity securitySignup = new SignupRequestForSecurity();
        securitySignup.setUsername(signupRequest.getUsername());
        securitySignup.setEmail(signupRequest.getEmail());
        securitySignup.setPassword(signupRequest.getPassword());
        securitySignup.setRoles(signupRequest.getRoles());

        securityClient.registerUser(securitySignup);



        AppUserEntity user = new AppUserEntity();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());

        List<Address> addressEntities = signupRequest.getAddresses().stream()
                .map(dto -> new Address(
                        dto.getCity(),
                        dto.getCountry(),
                        null,
                        dto.getPostalCode(),
                        dto.getState(),
                        dto.getStreet()
                ))
                .collect(Collectors.toList());

        user.setAddresses(addressEntities);

        AppUserEntity createdUser = userService.createUser(user);

        return ResponseEntity.ok(createdUser);
    }


    @GetMapping("{id}/orders")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<List<OrderDto>> getOrderHistory(@PathVariable Long id){
        return ResponseEntity.ok(orderClient.getUsersOrders(id));
    }

    @PostMapping("/{id}/wishlist")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> addToWishlist(@PathVariable Long id, @RequestBody WishlistItem item){
        userService.addToWishlist(id, item);
        return ResponseEntity.ok("Added to wishlist");
    }

    @PostMapping("/{id}/viewed")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> addViewed(@PathVariable Long id, @RequestBody RecentlyViewedProduct item){
        userService.addViewedProduct(id, item);
        return ResponseEntity.ok("Added to recently viewed");
    }
}
