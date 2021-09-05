package triple.assignment.clubmileage.service;

import triple.assignment.clubmileage.handler.ReviewEvent;
import triple.assignment.clubmileage.web.dto.ShowPointResponseDto;

import java.util.List;

public interface PointService {
    List<String> showAllUser();
    ShowPointResponseDto showPoint(String userId);
    void earnPoint(ReviewEvent reviewEvent);
}
