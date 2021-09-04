package triple.assignment.clubmileage.model.review;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "IMAGES")
public class Images {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "image_history_id", columnDefinition = "BINARY(16)")
    private UUID imageHistoryId;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(
    //        name = "review_history_id",
    //        nullable = false,
    //        foreignKey = @ForeignKey(name = "FK_IMAGES_review_history_id_REVIEWS_review_history_id")
    //)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "userId"),
            @JoinColumn(name = "placeId")
    })
    private Reviews review;

    @Column(name = "image_id", columnDefinition = "BINARY(16)")
    private UUID imageId;

    protected Images() {}

    public Images(UUID imageId, Reviews review) {
        this.imageId = imageId;
        this.review = review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }
}
