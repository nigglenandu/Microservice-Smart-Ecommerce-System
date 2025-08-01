package NiggleNandu.Recommendation_Service.Service;

import NiggleNandu.Recommendation_Service.Clients.OrderClient;
import NiggleNandu.Recommendation_Service.Clients.ProductClient;
import NiggleNandu.Recommendation_Service.Dtos.OrderDto;
import NiggleNandu.Recommendation_Service.Dtos.OrderItemDto;
import NiggleNandu.Recommendation_Service.Dtos.ProductDto;
import NiggleNandu.Recommendation_Service.Dtos.RecommendationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
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

    public List<RecommendationDto> getUserRecommendations(String userId){
        List<OrderDto> orders = orderClient.getUserOrders(userId);
        Set<Long> productIds = new HashSet<>();

        for(OrderDto order : orders){
            for(OrderItemDto item : order.getItems()){
                productIds.add(item.getProductId());
            }
        }

        return productIds.stream()
                .map(productClient::getProduct)
                .map(this::mapToRecommendation)
                .collect(Collectors.toList());
    }

    public List<RecommendationDto> getTrendingProducts(){
        List<Long> trendingIds = List.of(1L, 2L, 3L);

        return trendingIds.stream()
                .map(productClient::getProduct)
                .map(this::mapToRecommendation)
                .collect(Collectors.toList());
    }

    private RecommendationDto mapToRecommendation(ProductDto product){
        RecommendationDto dto = new RecommendationDto();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setRating(4.5);
        return dto;
    }
}
