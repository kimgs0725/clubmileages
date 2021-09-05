package triple.assignment.clubmileage.service;

import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import triple.assignment.clubmileage.handler.ReviewEvent;
import triple.assignment.clubmileage.model.points.PointHistory;
import triple.assignment.clubmileage.model.points.PointHistoryType;
import triple.assignment.clubmileage.model.points.PointsHistoryRepository;
import triple.assignment.clubmileage.model.review.ReviewRepository;
import triple.assignment.clubmileage.model.review.Reviews;
import triple.assignment.clubmileage.model.users.Users;
import triple.assignment.clubmileage.model.users.UsersRepository;
import triple.assignment.clubmileage.service.impl.PointServiceImpl;
import triple.assignment.clubmileage.web.dto.ShowPointDetailResponseDto;
import triple.assignment.clubmileage.web.dto.ShowPointResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PointsHistoryRepository pointsHistoryRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private PointServiceImpl pointService;

    @Test
    @DisplayName("유저 아이디의 포인트 히스토리를 조회한다.")
    void show_PointHistory_Test() {
        // given
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setUserId(userId);
        List<PointHistory> pointHistories = new ArrayList<>();
        pointHistories.add(new PointHistory(user, PointHistoryType.DEDUCTION, 1));
        pointHistories.add(new PointHistory(user, PointHistoryType.EARN, 3));
        given(usersRepository.findById(userId)).willReturn(Optional.of(user));
        given(pointsHistoryRepository.findPointHistoriesByUser(user)).willReturn(pointHistories);
        // when
        ShowPointResponseDto showPointResponseDto = pointService.showPoint(userId.toString());
        // then
        assertThat(showPointResponseDto.getCurrentPoint()).isEqualTo(2);
        assertThat(showPointResponseDto.getUserId()).isEqualTo(userId.toString());
        List<ShowPointDetailResponseDto> pointDetails = showPointResponseDto.getPointDetails();
        for (int i = 0; i < pointDetails.size(); i++) {
            assertThat(pointDetails.get(i).getUpdatePoint()).isEqualTo(pointHistories.get(i).getUpdatePoint());
            assertThat(pointDetails.get(i).getAction()).isEqualTo(pointHistories.get(i).getType().getValue());
        }
    }

    @Test
    @DisplayName("신규 유저의 포인트 히스토리는 비어있어야 한다.")
    void show_newUser_Test() {
        // given
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setUserId(userId);
        given(usersRepository.findById(userId)).willReturn(Optional.of(user));
        given(pointsHistoryRepository.findPointHistoriesByUser(user)).willReturn(new ArrayList<>());
        // when
        ShowPointResponseDto showPointResponseDto = pointService.showPoint(userId.toString());
        // then
        assertThat(showPointResponseDto.getCurrentPoint()).isEqualTo(0);
        assertThat(showPointResponseDto.getUserId()).isEqualTo(userId.toString());
        assertThat(showPointResponseDto.getPointDetails()).isEmpty();
    }

    @Test
    @DisplayName("리뷰 추가 이벤트가 전달된다.")
    void add_ReviewEvent_test() {
        // given
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        String content = "좋아요";
        ReviewEvent event = new ReviewEvent("REVIEW", "ADD",
                reviewId.toString(), content, userId.toString(), placeId.toString());
        Users user = new Users();
        user.setUserId(userId);
        given(usersRepository.findById(userId)).willReturn(Optional.of(user));
        given(reviewRepository.countReviewsByPlaceId(placeId)).willReturn(0);
        // when
        pointService.earnPoint(event);
        // then
        verify(usersRepository, times(1)).findById(userId);
        verify(reviewRepository, times(1)).countReviewsByPlaceId(placeId);
        verify(reviewRepository, times(1)).save(any());
    }
}
