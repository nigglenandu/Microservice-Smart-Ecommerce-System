package NiggleNandu.User_Service.Services;

import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.UserEntity;
import NiggleNandu.User_Service.Entity.WishlistItem;

import java.util.Optional;

public interface IUserService {
    Optional<UserEntity> getUserById(Long id);
    void addToWishlist(Long id, WishlistItem item);
    void addViewedProduct(Long id, RecentlyViewedProduct product);
}
