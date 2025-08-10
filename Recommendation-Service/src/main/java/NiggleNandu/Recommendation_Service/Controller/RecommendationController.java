package NiggleNandu.Recommendation_Service.Controller;

import NiggleNandu.Recommendation_Service.Dtos.RecommendationDto;
import NiggleNandu.Recommendation_Service.Service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    @Autowired
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationDto>> userRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<RecommendationDto>> trendingRecommendations(){
        return ResponseEntity.ok(recommendationService.getTrendingProducts());
    }
}
