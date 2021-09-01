package triple.assignment.clubmileage.model.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Reviews, UUID> {
    boolean existsReviewsByPlaceId(UUID placeId);
    Reviews findReviewsByReviewId(UUID reviewId);
}
