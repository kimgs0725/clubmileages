package triple.assignment.clubmileage.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import triple.assignment.clubmileage.service.PointService;

@Component
@RequiredArgsConstructor
public class ReviewEventListener {

    private final PointService pointService;

    @Async
    @EventListener
    public void onReviewWrite(ReviewEvent event) {
        pointService.earnPoint(event);
    }
}
