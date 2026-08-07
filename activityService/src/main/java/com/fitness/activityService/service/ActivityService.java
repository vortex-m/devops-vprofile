package com.fitness.activityService.service;


import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidation;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isValidUser = userValidation.validateUser(request.getUserId());
        if(!isValidUser){
            throw new RuntimeException("User is not valid: " + request.getUserId());
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .activityType(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try{
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);
        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("Saved info: {}", savedActivity);

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getActivityType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }
}












































//Flow kuch aisa hona chahiye:
//
//User Service
//
//↓
//
//Activity Service
//
//↓
//
//Kafka
//
//↓
//
//AI Service
//
//↓
//
//Notification Service
//
//↓
//
//Analytics Service
//
//Agar tum ye khud bana lete ho, to interview mein Kafka ke 80–90% practical questions confidently answer kar paoge. Ye sirf API use karna nahi hoga, balki Kafka ko real microservices architecture mein apply karna bhi aa jayega.