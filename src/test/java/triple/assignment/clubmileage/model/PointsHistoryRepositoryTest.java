package triple.assignment.clubmileage.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import triple.assignment.clubmileage.model.points.PointHistory;
import triple.assignment.clubmileage.model.points.PointHistoryType;
import triple.assignment.clubmileage.model.points.PointsHistoryRepository;
import triple.assignment.clubmileage.model.users.Users;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointsHistoryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PointsHistoryRepository pointsHistoryRepository;

    private Users user1;

    private Users user2;

    @BeforeEach
    void setUp() {
        user1 = new Users();
        user2 = new Users();
        user2.calculatePoint(PointHistoryType.EARN, 2);
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
    }

    @Test
    @DisplayName("포인트를 저장합니다.")
    void save_PointHistory_Test() {
        // given
        PointHistory pointHistory = new PointHistory(user1, PointHistoryType.EARN, 1);

        // when
        pointsHistoryRepository.save(pointHistory);
        UUID pointHistoryId = pointHistory.getPointHistoryId();
        testEntityManager.flush();

        // then
        PointHistory findPointHistory = testEntityManager.find(PointHistory.class, pointHistoryId);
        assertThat(findPointHistory.getUpdatePoint()).isEqualTo(1);
        assertThat(findPointHistory.getType()).isEqualTo(PointHistoryType.EARN);
    }

    @Test
    @DisplayName("포인트를 조회합니다.")
    void find_PointHistory_Test() {
        // given
        PointHistory pointHistory = new PointHistory(user1, PointHistoryType.EARN, 1);
        UUID pointHistoryId = (UUID) testEntityManager.persistAndGetId(pointHistory);

        // when
        PointHistory findPointHistory = pointsHistoryRepository.findById(pointHistoryId).orElseThrow();

        // then
        assertThat(findPointHistory.getPointHistoryId()).isEqualTo(pointHistoryId);
        assertThat(findPointHistory.getUpdatePoint()).isEqualTo(1);
        assertThat(findPointHistory.getType()).isEqualTo(PointHistoryType.EARN);
    }

    @Test
    @DisplayName("포인트를 차감합니다.")
    void deduct_Point_Test() {
        // given
        PointHistory pointHistory = new PointHistory(user2, PointHistoryType.DEDUCTION, 1);
        UUID pointHistoryId = pointsHistoryRepository.save(pointHistory).getPointHistoryId();
        testEntityManager.flush();
        // when
        PointHistory findPointHistory = testEntityManager.find(PointHistory.class, pointHistoryId);
        // then
        assertThat(findPointHistory.getPointHistoryId()).isEqualTo(pointHistoryId);
        assertThat(findPointHistory.getUpdatePoint()).isEqualTo(1);
        assertThat(findPointHistory.getType()).isEqualTo(PointHistoryType.DEDUCTION);
        assertThat(user2.getPoint()).isEqualTo(1);
    }

    @Test
    @DisplayName("user1의 포인트 히스토리를 조회합니다.")
    void show_PointHistory_Test() {
        // given
        PointHistory pointHistory1 = new PointHistory(user1, PointHistoryType.EARN, 2);
        pointsHistoryRepository.save(pointHistory1);
        PointHistory pointHistory2 = new PointHistory(user1, PointHistoryType.EARN, 1);
        pointsHistoryRepository.save(pointHistory2);
        PointHistory pointHistory3 = new PointHistory(user1, PointHistoryType.DEDUCTION, 1);
        pointsHistoryRepository.save(pointHistory3);
        testEntityManager.flush();
        // when
        List<PointHistory> pointHistories = pointsHistoryRepository.findPointHistoriesByUser(user1);
        // then
        assertThat(pointHistories).hasSize(3);
        assertThat(user1.getPoint()).isEqualTo(2);
        assertThat(pointHistories.get(0).getType()).isEqualTo(PointHistoryType.DEDUCTION);
        assertThat(pointHistories.get(0).getUpdatePoint()).isEqualTo(1);
        assertThat(pointHistories.get(1).getType()).isEqualTo(PointHistoryType.EARN);
        assertThat(pointHistories.get(1).getUpdatePoint()).isEqualTo(1);
        assertThat(pointHistories.get(2).getType()).isEqualTo(PointHistoryType.EARN);
        assertThat(pointHistories.get(2).getUpdatePoint()).isEqualTo(2);
    }
}