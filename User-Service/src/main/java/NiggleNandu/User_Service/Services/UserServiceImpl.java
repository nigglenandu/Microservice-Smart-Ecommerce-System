package NiggleNandu.User_Service.Services;

import NiggleNandu.User_Service.Entity.RecentlyViewedProduct;
import NiggleNandu.User_Service.Entity.UserEntity;
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
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void addToWishlist(Long id, WishlistItem item) {
        UserEntity user = getUserById(id);
        user.getWishlist().add(item);
        userRepo.save(user);
    }

    @Override
    public void addViewedProduct(Long id, RecentlyViewedProduct product) {
        UserEntity user = getUserById(id);
        product.setViewedAt(LocalDateTime.now());
        user.getRecentlyViewed().add(product);
        userRepo.save(user);
    }
}
