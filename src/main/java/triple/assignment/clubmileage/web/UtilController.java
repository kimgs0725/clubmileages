package triple.assignment.clubmileage.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import triple.assignment.clubmileage.service.PointService;

import java.util.List;

@RestController
@RequestMapping("/util")
@RequiredArgsConstructor
public class UtilController {

    private final PointService pointService;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<String> showAllUser() {
        return pointService.showAllUser();
    }
}
