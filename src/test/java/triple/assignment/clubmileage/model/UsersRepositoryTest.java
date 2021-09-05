package triple.assignment.clubmileage.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import triple.assignment.clubmileage.model.points.PointHistoryType;
import triple.assignment.clubmileage.model.users.Users;
import triple.assignment.clubmileage.model.users.UsersRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsersRepository usersRepository;

    private Logger log = LoggerFactory.getLogger(UsersRepositoryTest.class);

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
    @DisplayName("유저를 전부 조회합니다.")
    void find_All_Test() {
        // given
        // when
        List<Users> findUsers = usersRepository.findAll();
        // then
        assertThat(findUsers).hasSize(2);
        for (Users findUser : findUsers) {
            log.info("findUser.userId = {}", findUser.getUserId());
        }
    }

    @Test
    @DisplayName("유저를 저장합니다.")
    void save_User_Test() {
        // given
        Users user = new Users();

        // when
        usersRepository.save(user);
        UUID userId = user.getUserId();
        testEntityManager.flush();

        // then
        Users findUser = testEntityManager.find(Users.class, userId);
        assertThat(findUser.getPoint()).isZero();
        assertThat(findUser.getUserId()).isNotNull();
        log.info("user.point = {}", findUser.getPoint());
        log.info("user.userId = {}", findUser.getUserId());
    }

    @Test
    @DisplayName("유저 아이디를 조회합니다.")
    void find_By_UserId_Test() {
        // given
        UUID userId = user1.getUserId();

        // when
        Users findUser = usersRepository.findById(userId).orElseThrow();

        // then
        assertThat(findUser.getPoint()).isZero();
    }

    @Test
    @DisplayName("유저 포인트를 조회합니다.")
    void find_By_UserId_And_Point_Test() {
        // given
        UUID userId = user2.getUserId();

        // when
        Users findUser = usersRepository.findById(userId).orElseThrow();

        // then
        assertThat(findUser.getPoint()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저아이디를 조회하여 포인트를 업데이트합니다.")
    void update_Point_Test() {
        // given
        UUID userId = user1.getUserId();
        Users users = testEntityManager.find(Users.class, userId);

        // when
        users.calculatePoint(PointHistoryType.EARN, 3);
        usersRepository.flush();

        // then
        Users findUser = testEntityManager.find(Users.class, userId);
        assertThat(findUser.getPoint()).isEqualTo(3);
    }
}