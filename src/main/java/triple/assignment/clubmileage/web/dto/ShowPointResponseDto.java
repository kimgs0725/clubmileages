package triple.assignment.clubmileage.web.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShowPointResponseDto {
    private String userId;
    private Integer currentPoint;
    private List<ShowPointDetailResponseDto> pointDetails = new ArrayList<>();
}
