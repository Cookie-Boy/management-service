package ru.platform.management.core.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.platform.management.core.model.entity.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByDoctorIdOrderByCreatedAtDesc(UUID doctorId);
}