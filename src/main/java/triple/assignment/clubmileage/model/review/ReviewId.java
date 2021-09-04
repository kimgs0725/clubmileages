package triple.assignment.clubmileage.model.review;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class ReviewId implements Serializable {
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "place_id", columnDefinition = "BINARY(16)")
    private UUID placeId;

    protected ReviewId() {}

    public ReviewId(UUID userId, UUID placeId) {
        this.userId = userId;
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(userId, reviewId.userId) && Objects.equals(placeId, reviewId.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId);
    }
}
