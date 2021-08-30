package triple.assignment.clubmileage.model.points;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import triple.assignment.clubmileage.model.users.Users;

import java.util.List;
import java.util.UUID;

public interface PointsHistoryRepository extends JpaRepository<PointHistory, UUID> {

    @Query("select p from PointHistory p where p.user = :user order by p.createDatetime desc")
    List<PointHistory> findPointHistoriesByUser(@Param("user") Users user);
}
