package triple.assignment.clubmileage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.assignment.clubmileage.handler.ReviewEvent;
import triple.assignment.clubmileage.model.points.PointHistory;
import triple.assignment.clubmileage.model.points.PointHistoryType;
import triple.assignment.clubmileage.model.points.PointsHistoryRepository;
import triple.assignment.clubmileage.model.users.Users;
import triple.assignment.clubmileage.model.users.UsersRepository;
import triple.assignment.clubmileage.service.PointService;
import triple.assignment.clubmileage.web.dto.ShowPointDetailResponseDto;
import triple.assignment.clubmileage.web.dto.ShowPointResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final UsersRepository usersRepository;

    private final PointsHistoryRepository pointsHistoryRepository;

    /**
     * 유저가 가지고 있는 포인트 현황을 반환한다.
     * 현재 가지고 있는 포인트와 포인트 히스토리를 최근 순으로 반환
     * @param userId
     * @return
     */
    @Override
    public ShowPointResponseDto showPoint(String userId) {
        Users user = usersRepository.findById(UUID.fromString(userId)).orElseThrow();
        List<PointHistory> pointHistories = pointsHistoryRepository.findPointHistoriesByUser(user);
        ShowPointResponseDto responseDto = new ShowPointResponseDto();
        responseDto.setUserId(userId);
        responseDto.setCurrentPoint(pointHistories.get(0).getCurrentPoint());
        List<ShowPointDetailResponseDto> detailResponseDtos = new ArrayList<>();
        for (PointHistory pointHistory : pointHistories) {
            ShowPointDetailResponseDto detailResponseDto = new ShowPointDetailResponseDto();
            detailResponseDto.setAction(pointHistory.getType().getValue());
            detailResponseDto.setUpdatePoint(pointHistory.getUpdatePoint());
            detailResponseDto.setCurrentPoint(pointHistory.getCurrentPoint());
        }
        responseDto.setPointDetails(detailResponseDtos);
        return responseDto;
    }

    /**
     * 리뷰 작성 이벤트를 핸들링 합니다.
     * ReviewEvent.action에 따라 동작을 결정합니다.
     * @param event
     */
    @Override
    @Transactional
    public void earnPoint(ReviewEvent event) {
        Users user = findByUserId(event);
        switch (event.getAction()) {
            case "ADD":
                addPoint(user, event);
                break;
            case "MOD":
                updatePoint(user, event);
                break;
            case "DELETE":
                deletePoint(user, event);
                break;
        }
    }

    private Users findByUserId(ReviewEvent request) {
        UUID userId = UUID.fromString(request.getUserId());
        Optional<Users> byUser = usersRepository.findById(userId);
        return byUser.orElseThrow();
    }

    private void addPoint(Users user, ReviewEvent event) {
    }

    private Integer accumulatePoint(ReviewEvent request) {
        int point = (!request.getContent().isEmpty() ? 1 : 0);
        point += (!request.getAttachedPhotoIds().isEmpty() ? 1 : 0);
        return point;
    }

    private PointHistory createPointHistory(Users user, Integer point) {
        PointHistory pointHistory = new PointHistory(PointHistoryType.EARN, point, point);
        pointHistory.setUser(user);
        return pointHistory;
    }

    /**
     * 리뷰를 업데이트한다.
     * 다음과 같은 상황에서 포인트를 +/- 한다.
     *  - '+' 인 경우
     *      - 글만 있는 리뷰에 사진을 추가했을 때 (+1)
     *  - '-'인 경우
     *      - 글+사진 리뷰에서 사진을 모두 삭제할 때 (-1)
     *
     * @param user
     * @param event
     */
    private void updatePoint(Users user, ReviewEvent event) {
    }

    /**
     * 리뷰를 삭제한다.
     * 해당 리뷰를 써서 얻은 포인트를 회수한다.
     * @param user
     * @param event
     */
    private void deletePoint(Users user, ReviewEvent event) {}

}
