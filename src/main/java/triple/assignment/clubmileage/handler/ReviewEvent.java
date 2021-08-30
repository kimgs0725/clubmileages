package triple.assignment.clubmileage.handler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewEvent {
    private String type;

    private String action;

    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds = new ArrayList<>();

    private String userId;

    private String placeId;

    public ReviewEvent(String type, String action, String reviewId, String content, String userId, String placeId) {
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.userId = userId;
        this.placeId = placeId;
    }
}
