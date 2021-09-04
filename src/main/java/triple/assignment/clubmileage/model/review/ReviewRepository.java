package triple.assignment.clubmileage.model.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Reviews, ReviewId> {
    @Query("select count(r) from Reviews r where r.reviewHistoryId.placeId = :placeId")
    int existsReviewsByPlaceId(@Param(value = "placeId") UUID placeId);
    Reviews findReviewsByReviewId(UUID reviewId);
}
