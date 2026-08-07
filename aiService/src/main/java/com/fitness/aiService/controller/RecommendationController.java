package com.fitness.aiService.controller;

import com.fitness.aiService.dto.ActivityResponse;
import com.fitness.aiService.dto.UserResponse;
import com.fitness.aiService.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> getUserResponse(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityResponse(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getRecommendationActivity(activityId));
    }
}
