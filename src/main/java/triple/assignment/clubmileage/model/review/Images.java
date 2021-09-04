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
    @Column(name = "image_id", columnDefinition = "BINARY(16)")
    private UUID imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "userId"),
            @JoinColumn(name = "placeId")
    })
    private Reviews review;

    protected Images() {}

    public Images(UUID imageId, Reviews review) {
        this.imageId = imageId;
        this.review = review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }
}
