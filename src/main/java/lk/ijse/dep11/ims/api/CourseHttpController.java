package lk.ijse.dep11.ims.api;

import lk.ijse.dep11.ims.to.CourseTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseHttpController {

    

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public CourseTO createCourse(@RequestBody CourseTO course){
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{courseId}", consumes = "application/json")
    public void updateCourse(@RequestBody CourseTO course,
                                 @PathVariable int courseId){}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping( "/{courseId}")
    public void deleteCourse(@PathVariable int courseId){}

    @GetMapping(value = "/{courseId}", produces = "application/json")
    public CourseTO getCourseDetails(){
        return null;
    }

    @GetMapping( produces = "application/json")
    public List<CourseTO> getAllCourses(){
        return null;
    }

}
