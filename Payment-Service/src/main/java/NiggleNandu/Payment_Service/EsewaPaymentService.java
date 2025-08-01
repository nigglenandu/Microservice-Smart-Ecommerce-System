package NiggleNandu.Payment_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EsewaPaymentService {
    private static final Logger logger = LoggerFactory.getLogger(EsewaPaymentService.class);

    @Value("{esewa.test.url}")
    private String esewaUrl;

    @Value("{esewa.merchant.id}")
    private String merchantId;

    @Value("${esewa.success.url}")
    private String successUrl;

    @Value("${esewa.failure.url}")
    private String failureUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EsewaPaymentService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public PaymentFormFieldResponse initiatePayment(PaymentRequest request) {
        if ("true".equals(System.getenv("Mock_ESEWA"))) {
            logger.info("Using mock mode for payment initiation");
            String transactionUuid = UUID.randomUUID().toString();
            double totalAmount = calculateTotalAmount(request);
            Map<String, String> formFields = new HashMap<>();
            formFields.put("amount", String.format("%.2f", request.getAmount()));
            formFields.put("tax_amount", String.format("%.2f", request.getTaxAmount()));
            formFields.put("product_service_charge", String.format("%.2f", request.getServiceCharge()));
            formFields.put("product_delivery_charge", String.format("%.2f", request.getDeliveryCharge()));
            formFields.put("transaction_uuid", transactionUuid);
            formFields.put("product_code", merchantId);
            formFields.put("success_url", successUrl + "?transaction_uuid=" + transactionUuid);
            formFields.put("failure_url", failureUrl);
            PaymentFormFieldResponse response = new PaymentFormFieldResponse();
            response.setEsewaUrl("hhtps://mock-esewa.com");
            response.setFormField(formFields);
            return response;
        }

        validatePaymentRequest(request);
        String transactionUuid = UUID.randomUUID().toString();
        double totalAmount = calculateTotalAmount(request);

        Map<String, String> formFields = new HashMap<>();
        formFields.put("amount", String.format("%.2f", request.getAmount()));
        formFields.put("tax_amount", String.format("%.2f", request.getTaxAmount()));
        formFields.put("product_service_charge", String.format("%.2f", request.getServiceCharge()));
        formFields.put("product_delivery_charge", String.format("%.2f", request.getDeliveryCharge()));
        formFields.put("total_amount", String.format("%.2f", totalAmount));
        formFields.put("transaction_uuid", transactionUuid);
        formFields.put("product_code", merchantId);
        formFields.put("success_url", successUrl + "?transaction_uuid=" + transactionUuid);
        formFields.put("failure_url", failureUrl);
        formFields.put("signed_field_names", "total_Amount, transaction_uuid, product_code");

        String signatureData = buildSignatureData(totalAmount, transactionUuid);
        String signature = generateSignature(signatureData, "8gBm/:&EnhH.1/q");
        formFields.put("signature", signature);

        PaymentFormFieldResponse response = new PaymentFormFieldResponse();
        response.setEsewaUrl(esewaUrl);
        response.setFormField(formFields);

        return response;
    }

    private void validatePaymentRequest(PaymentRequest request){
        if(request.getAmount() < 0 || request.getTaxAmount() < 0 ||
        request.getServiceCharge() < 0 || request.getDeliveryCharge() < 0){
            throw new IllegalArgumentException("All payment amounts must be not-negative");
        }
        if(!merchantId.equals(request.getProductCode())){
            throw new IllegalArgumentException("Product code must match merchant ID: " + merchantId);
        }
    }


}
