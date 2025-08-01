package NiggleNandu.Payment_Service;

import java.util.HashMap;
import java.util.Map;

public class PaymentFormFieldResponse {
    private String esewaUrl;
    private Map<String, String> FormField = new HashMap<>();

    public String getEsewaUrl() {
        return esewaUrl;
    }

    public void setEsewaUrl(String esewaUrl) {
        this.esewaUrl = esewaUrl;
    }

    public Map<String, String> getFormField() {
        return FormField;
    }

    public void setFormField(Map<String, String> formField) {
        FormField = formField;
    }
}
