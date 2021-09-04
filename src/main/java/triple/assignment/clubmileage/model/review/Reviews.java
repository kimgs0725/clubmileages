package triple.assignment.clubmileage.model.review;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(
        name = "REVIEWS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"review_id"}
        )
)
public class Reviews {
    @EmbeddedId
    private ReviewId reviewHistoryId;

    @Column(name = "review_id", columnDefinition = "BINARY(16)")
    private UUID reviewId;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();

    @Column(name = "is_first_review")
    private boolean firstReview = false;

    protected Reviews() {}

    public Reviews(UUID userId, UUID reviewId, UUID placeId, String content) {
        this.reviewHistoryId = new ReviewId(userId, placeId);
        this.reviewId = reviewId;
        this.content = content;
    }

    public void addImage(Images image) {
        if (!images.contains(image)) {
            images.add(image);
            image.setReview(this);
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void setFirstReview(boolean firstReview) {
        this.firstReview = firstReview;
    }
}
