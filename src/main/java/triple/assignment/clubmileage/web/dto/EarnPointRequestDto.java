package triple.assignment.clubmileage.web.dto;

import lombok.Data;
import triple.assignment.clubmileage.handler.ReviewEvent;

import java.util.ArrayList;
import java.util.List;

@Data
public class EarnPointRequestDto {
    private String type;

    private String action;

    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds = new ArrayList<>();

    private String userId;

    private String placeId;

    public ReviewEvent toEvent() {
        ReviewEvent reviewEvent = new ReviewEvent(
                type, action, reviewId, content, userId, placeId
        );
        reviewEvent.setAttachedPhotoIds(attachedPhotoIds);
        return reviewEvent;
    }
}
