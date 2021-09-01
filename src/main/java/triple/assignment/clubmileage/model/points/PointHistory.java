package triple.assignment.clubmileage.model.points;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import triple.assignment.clubmileage.model.users.Users;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PointHistory {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "point_history_id", columnDefinition = "BINARY(16)")
    private UUID pointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_POINT_HISTORY_user_id_USERS_user_id")
    )
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PointHistoryType type;

    @Column(name = "update_point")
    private Integer updatePoint;

    @Column(name = "create_datetime")
    @CreatedDate
    private LocalDateTime createDatetime;

    public PointHistory(PointHistoryType type, Integer updatePoint) {
        this.type = type;
        this.updatePoint = updatePoint;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
