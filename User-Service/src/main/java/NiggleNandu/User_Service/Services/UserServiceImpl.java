package NiggleNandu.User_Service.Services;

import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.AppUserEntity;
import NiggleNandu.User_Service.Entity.WishlistItem;
import NiggleNandu.User_Service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public Optional<AppUserEntity> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public AppUserEntity createUser(AppUserEntity user) {
        return userRepo.save(user);
    }

    @Override
    public void addToWishlist(Long id, WishlistItem item) {
        AppUserEntity user = getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        user.getWishlist().add(item);
        userRepo.save(user);
    }

    @Override
    public void addViewedProduct(Long id, RecentlyViewedProduct product) {
        AppUserEntity user = getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        product.setViewedAt(LocalDateTime.now());
        user.getRecentlyViewed().add(product);
        userRepo.save(user);
    }
}
