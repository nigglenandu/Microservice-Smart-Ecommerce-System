package NiggleNandu.Recommendation_Service.Service;

import NiggleNandu.Recommendation_Service.Clients.OrderClient;
import NiggleNandu.Recommendation_Service.Clients.ProductClient;
import NiggleNandu.Recommendation_Service.Dtos.OrderDto;
import NiggleNandu.Recommendation_Service.Dtos.OrderItemDto;
import NiggleNandu.Recommendation_Service.Dtos.ProductDto;
import NiggleNandu.Recommendation_Service.Dtos.RecommendationDto;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private ProductClient productClient;

    private ProductDto safeGetProduct(Long id) {
        try {
            return productClient.getProduct(id);
        } catch (FeignException.NotFound e) {
            System.out.println("Product not found: " + id);
            return null;
        } catch (Exception e) {
            System.out.println("Error fetching product: " + id + " -> " + e.getMessage());
            return null;
        }
    }

    private RecommendationDto mapToRecommendation(ProductDto product){
        RecommendationDto dto = new RecommendationDto();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setRating(4.5); // you can calculate actual rating later
        return dto;
    }

    public List<RecommendationDto> getUserRecommendations(String userId){
        List<OrderDto> orders;
        try {
            orders = orderClient.getUserOrders(userId);
        } catch (FeignException.NotFound e) {
            System.out.println("No orders found for user: " + userId);
            return List.of();
        }

        Set<Long> productIds = new HashSet<>();
        for(OrderDto order : orders){
            for(OrderItemDto item : order.getItems()){
                productIds.add(item.getProductId());
            }
        }

        return productIds.stream()
                .map(this::safeGetProduct)
                .filter(p -> p != null)
                .map(this::mapToRecommendation)
                .collect(Collectors.toList());
    }

    public List<RecommendationDto> getTrendingProducts(){
        List<Long> trendingIds = List.of(1L, 2L, 3L);

        return trendingIds.stream()
                .map(this::safeGetProduct)
                .filter(p -> p != null)
                .map(this::mapToRecommendation)
                .collect(Collectors.toList());
    }
}
