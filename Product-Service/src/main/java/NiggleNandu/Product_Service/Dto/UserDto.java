package NiggleNandu.Product_Service.Dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String email;


    private Set<String> role;

    private List<AddressDto> addresses;

    private List<WishlistItemDto> wishlist;

    private List<RecentlyViewedProductDto> recentlyViewed;


    public List<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDto> addresses) {
        this.addresses = addresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RecentlyViewedProductDto> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(List<RecentlyViewedProductDto> recentlyViewed) {
        this.recentlyViewed = recentlyViewed;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WishlistItemDto> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<WishlistItemDto> wishlist) {
        this.wishlist = wishlist;
    }
}
