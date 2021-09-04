package triple.assignment.clubmileage.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import triple.assignment.clubmileage.handler.ReviewEvent;
import triple.assignment.clubmileage.service.PointService;
import triple.assignment.clubmileage.web.dto.EarnPointRequestDto;
import triple.assignment.clubmileage.web.dto.ShowPointResponseDto;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final PointService pointService;

    @GetMapping("/point/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ShowPointResponseDto showPoint(@PathVariable String userId) {
        return pointService.showPoint(userId);
    }

    @PostMapping("/point")
    @ResponseStatus(HttpStatus.OK)
    public void earnPoint(@RequestBody EarnPointRequestDto requestDto) {
        ReviewEvent reviewEvent = requestDto.toEvent();
        pointService.earnPoint(reviewEvent);
    }
}
