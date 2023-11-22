package lk.ijse.dep11.ims.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseHttpController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public void createCourse(){}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/${courseID}", consumes = "application/json")
    public void updateCourse(){}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/${courseID}")
    public void deleteCourse(){}

    @GetMapping(value = "/${courseID}", produces = "application/json")
    public void getCourseDetails(){}

    @GetMapping(value = "/${courseID}", produces = "application/json")
    public void getAllCourses(){}

}
