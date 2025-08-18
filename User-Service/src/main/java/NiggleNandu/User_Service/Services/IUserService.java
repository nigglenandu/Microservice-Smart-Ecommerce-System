package NiggleNandu.User_Service.Services;

import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.AppUserEntity;
import NiggleNandu.User_Service.Entity.WishlistItem;

import java.util.Optional;

public interface IUserService {
    Optional<AppUserEntity> getUserById(Long id);
    AppUserEntity createUser(AppUserEntity user);
    void addToWishlist(Long id, WishlistItem item);
    void addViewedProduct(Long id, RecentlyViewedProduct product);
}
