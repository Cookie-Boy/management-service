package ru.platform.management.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.management.api.dto.ReviewRequestDto;
import ru.platform.management.core.model.entity.Review;
import ru.platform.management.core.service.ReviewService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/management/doctors/{doctorId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@PathVariable UUID doctorId) {
        List<Review> reviews = reviewService.getReviewsByDoctorId(doctorId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@PathVariable UUID doctorId,
                                            @Valid @RequestBody ReviewRequestDto requestDto) {
        log.info("GOT THE REQUEST");
//        return ResponseEntity.ok().build();
        Review created = reviewService.createReview(doctorId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
