package com.fitness.aiService.service;

import com.fitness.aiService.dto.ActivityResponse;
import com.fitness.aiService.dto.UserResponse;
import com.fitness.aiService.model.Recommendation;
import com.fitness.aiService.repository.RecommendationRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public UserResponse getUserRecommendation(String userId){
        List<Recommendation> recommendations =  recommendationRepository.findByUserId(userId);

        List<ActivityResponse> activities = recommendations.stream()
                .map(r -> ActivityResponse.builder()
                        .activityId(r.getActivityId())
                        .activityType(r.getActivityType())
                        .recommendation(r.getRecommendation())
                        .improvements(r.getImprovements())
                        .suggestions(r.getSuggestions())
                        .safety(r.getSafety())
                        .build())
                .toList();

        return UserResponse.builder()
                .userId(userId)
                .activities(activities)
                .build();
    }

    public ActivityResponse getRecommendationActivity(String activityId){
        Recommendation recommendation =  recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("Activity is not present: " + activityId));

        return ActivityResponse.builder()
                .recommendation(recommendation.getRecommendation())
                .improvements(recommendation.getImprovements())
                .suggestions(recommendation.getSuggestions())
                .safety(recommendation.getSafety())
                .activityId(recommendation.getActivityId())
                .activityType(recommendation.getActivityType())
                .build();
    }
}
