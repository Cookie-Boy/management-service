package ru.platform.management.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.management.api.dto.ReviewRequestDto;
import ru.platform.management.core.model.entity.Review;
import ru.platform.management.core.repository.mongo.ReviewRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DoctorService doctorService;

    public List<Review> getReviewsByDoctorId(UUID doctorId) {
        if (doctorService.existsById(doctorId)) {
            throw new IllegalArgumentException("Doctor not found with id: " + doctorId);
        }
        return reviewRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
    }

    public Review createReview(UUID doctorIdFromPath, ReviewRequestDto dto) {
        if (!doctorIdFromPath.equals(dto.getDoctorId())) {
            throw new IllegalArgumentException(
                    String.format("Doctor ID in path (%s) does not match doctorId in request body (%s)",
                            doctorIdFromPath, dto.getDoctorId())
            );
        }

        if (doctorService.existsById(doctorIdFromPath)) {
            throw new IllegalArgumentException("Doctor not found with id: " + doctorIdFromPath);
        }

        Review review = Review.builder()
                .doctorId(dto.getDoctorId())
                .authorName(dto.getAuthorName())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        return reviewRepository.save(review);
    }
}