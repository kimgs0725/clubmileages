package triple.assignment.clubmileage.model.users;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import triple.assignment.clubmileage.model.points.PointHistoryType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
public class Users {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "cur_point", columnDefinition = "integer DEFAULT 0")
    private Integer point = 0;

    public void calculatePoint(PointHistoryType type, Integer point) {
        if (type == PointHistoryType.EARN) {
            this.point += point;
        } else {
            this.point -= point;
        }
    }
}
