package NiggleNandu.User_Service.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> role;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<WishlistItem> wishlist = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecentlyViewedProduct> recentlyViewed = new ArrayList<>();

    public UserEntity(List<Address> addresses, String email, Long id, List<RecentlyViewedProduct> recentlyViewed, Set<String> role, String username, List<WishlistItem> wishlist) {
        this.addresses = addresses;
        this.email = email;
        this.id = id;
        this.recentlyViewed = recentlyViewed;
        this.role = role;
        this.username = username;
        this.wishlist = wishlist;
    }

    public UserEntity() {
    }

    public List<Address> getAddresses() {   
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
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

    public List<RecentlyViewedProduct> getRecentlyViewed() {
        return recentlyViewed;
    }

    public void setRecentlyViewed(List<RecentlyViewedProduct> recentlyViewed) {
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

    public List<WishlistItem> getWishList() {
        return wishlist;
    }

    public void setWishList(List<WishlistItem> wishList) {
        this.wishlist = wishlist;
    }
}
