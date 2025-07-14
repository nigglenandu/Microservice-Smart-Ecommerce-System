package NiggleNandu.Cart_Service.Dto;

import NiggleNandu.Cart_Service.Entity.CartItem;

import java.util.List;

public class CartResponse {
    private List<CartItemDto> cartItemList;
    private double total;
    private double discount;



    public List<CartItemDto> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDto> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
