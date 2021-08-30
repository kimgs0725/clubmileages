package triple.assignment.clubmileage.service;

import triple.assignment.clubmileage.handler.ReviewEvent;
import triple.assignment.clubmileage.web.dto.ShowPointResponseDto;

public interface PointService {
    ShowPointResponseDto showPoint(String userId);
    void earnPoint(ReviewEvent reviewEvent);
}
