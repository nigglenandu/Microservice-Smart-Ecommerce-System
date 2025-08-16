package NiggleNandu.Order_Service.Controller;

import NiggleNandu.Order_Service.Dtos.OrderDto;
import NiggleNandu.Order_Service.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> place(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
