package triple.assignment.clubmileage.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import triple.assignment.clubmileage.model.review.ReviewId;
import triple.assignment.clubmileage.model.review.ReviewRepository;
import triple.assignment.clubmileage.model.review.Reviews;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰를 등록합니다.")
    void add_Reviews_Test() {
        // given
        UUID userId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        String content = "좋아요";
        Reviews review = new Reviews(userId, reviewId, placeId, content);
        // when
        ReviewId reviewHistoryId = reviewRepository.save(review).getReviewHistoryId();
        testEntityManager.flush();
        // then
        Reviews findReview = testEntityManager.find(Reviews.class, reviewHistoryId);
        assertThat(findReview.getContent()).isEqualTo(content);
        assertThat(findReview.getReviewId()).isEqualTo(reviewId);
        assertThat(findReview.getReviewHistoryId().getUserId()).isEqualTo(userId);
        assertThat(findReview.getReviewHistoryId().getPlaceId()).isEqualTo(placeId);
    }

    @Test
    @DisplayName("리뷰를 조회합니다.")
    void find_Reviews_Test() {
        // given
        UUID userId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        String content = "좋아요";
        Reviews review = new Reviews(userId, reviewId, placeId, content);
        ReviewId reviewHistoryId = testEntityManager.persistAndGetId(review, ReviewId.class);
        testEntityManager.flush();
        // when
        Reviews findReview = reviewRepository.findById(reviewHistoryId).orElseThrow();
        // then
        assertThat(findReview.getContent()).isEqualTo(content);
        assertThat(findReview.getReviewId()).isEqualTo(reviewId);
        assertThat(findReview.getReviewHistoryId().getUserId()).isEqualTo(userId);
        assertThat(findReview.getReviewHistoryId().getPlaceId()).isEqualTo(placeId);
    }

    @Test
    @DisplayName("리뷰아이디를 통해 조회합니다.")
    void find_By_ReviewId_Test() {
        // given
        UUID userId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        String content = "좋아요";
        Reviews review = new Reviews(userId, reviewId, placeId, content);
        ReviewId reviewHistoryId = testEntityManager.persistAndGetId(review, ReviewId.class);
        testEntityManager.flush();
        // when
        Reviews findReview = reviewRepository.findReviewsByReviewId(reviewId);
        // then
        assertThat(findReview.getContent()).isEqualTo(content);
        assertThat(findReview.getReviewId()).isEqualTo(reviewId);
        assertThat(findReview.getReviewHistoryId().getUserId()).isEqualTo(userId);
        assertThat(findReview.getReviewHistoryId().getPlaceId()).isEqualTo(placeId);
    }

    @Test
    @DisplayName("임의의 장소에 대해 리뷰한 갯수를 리턴합니다. (리뷰가 없을 때)")
    void count_Review_For_Place_Test1() {
        // given
        UUID placeId = UUID.randomUUID();
        // when
        int count = reviewRepository.countReviewsByPlaceId(placeId);
        // then
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("임의의 장소에 대해 리뷰한 갯수를 리턴합니다. (리뷰가 있을 때)")
    void count_Review_For_Place_Test2() {
        // given
        UUID userId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID placeId = UUID.randomUUID();
        String content = "좋아요";
        Reviews review = new Reviews(userId, reviewId, placeId, content);
        ReviewId reviewHistoryId = testEntityManager.persistAndGetId(review, ReviewId.class);
        testEntityManager.flush();
        // when
        int count = reviewRepository.countReviewsByPlaceId(placeId);
        // then
        assertThat(count).isEqualTo(1);
    }
}