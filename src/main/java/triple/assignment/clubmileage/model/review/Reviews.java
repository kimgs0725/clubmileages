package triple.assignment.clubmileage.model.review;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Reviews {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "review_history_id", columnDefinition = "BINARY(16)")
    private UUID reviewHistoryId;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "review_id", columnDefinition = "BINARY(16)")
    private UUID reviewId;

    @Column(name = "place_id", columnDefinition = "BINARY(16)")
    private UUID placeId;

    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();

    protected Reviews() {}

    public Reviews(UUID userId, UUID reviewId, UUID placeId, String content) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.placeId = placeId;
        this.content = content;
    }
}
