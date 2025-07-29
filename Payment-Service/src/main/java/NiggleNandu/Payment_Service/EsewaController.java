package NiggleNandu.Payment_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:63342")
public class EsewaController {

    private static final Logger logger = LoggerFactory.getLogger(EsewaController.class);

    private final EsewaPaymentService paymentService;
    private final ObjectMapper objectMapper;

    public EsewaController(ObjectMapper objectMapper, EsewaPaymentService paymentService){
        this.objectMapper = objectmapper;
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentFromFieldResponse> initiatePayment(@Value @RequestBody PaymentRequest request){
        logger.info("Initiating payment: amount={}, productCode={}", request.getAmount(), request.getProductCode());
        return ResponseEntity.ok(paymentService.initiatePayment(request));
    }
}
