package NiggleNandu.User_Service.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleStatus> roles;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<WishlistItem> wishlist = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecentlyViewedProduct> recentlyViewed = new ArrayList<>();

    public AppUserEntity(List<Address> addresses, String email, Long id, List<RecentlyViewedProduct> recentlyViewed, Set<RoleStatus> roles, String username, List<WishlistItem> wishlist) {
        this.addresses = addresses;
        this.email = email;
        this.id = id;
        this.recentlyViewed = recentlyViewed;
        this.roles = roles;
        this.username = username;
        this.wishlist = wishlist;
    }

    public AppUserEntity() {
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

    public Set<RoleStatus> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleStatus> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<WishlistItem> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<WishlistItem> wishlist) {
        this.wishlist = wishlist;
    }

}
