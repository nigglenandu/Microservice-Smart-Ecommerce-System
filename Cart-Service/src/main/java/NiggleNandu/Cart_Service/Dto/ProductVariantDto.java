package NiggleNandu.Cart_Service.Dto;

public class ProductVariantDto {
    private String size;
    private String color;

    public ProductVariantDto() {}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
