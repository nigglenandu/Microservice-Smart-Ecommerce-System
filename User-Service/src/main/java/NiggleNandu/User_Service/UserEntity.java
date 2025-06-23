package NiggleNandu.User_Service;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

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
    private List<WishListItem> wishList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<RecentlyViewedProduct> recentlyViewed = new ArrayList<>();


}
