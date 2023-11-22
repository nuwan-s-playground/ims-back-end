package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.CourseTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseHttpController {

    private HikariDataSource pool;
    public CourseHttpController(){
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("mysql");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_ims");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public CourseTO createCourse(@RequestBody @Validated(CourseTO.Create.class) CourseTO course){
        try (Connection connection = pool.getConnection()){
            PreparedStatement stm =
                    connection.prepareStatement("INSERT INTO course(name, duration_in_months) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, course.getName());
            stm.setInt(2, course.getDuration_in_months());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            course.setCourseId(id);
            return course;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{courseId}", consumes = "application/json")
    public void updateCourse(@RequestBody @Validated(CourseTO.Update.class) CourseTO course,
                                 @PathVariable int courseId){
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stmExists = connection.
                    prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExists.setInt(1, courseId);
            if(!stmExists.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
            }

            PreparedStatement stm = connection.
                    prepareStatement("UPDATE course SET name = ?, duration_in_months = ? WHERE id =?");
            stm.setString(1, course.getName());
            stm.setInt(2, course.getDuration_in_months());
            stm.setInt(3, courseId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping( "/{courseId}")
    public void deleteCourse(@PathVariable int courseId){

        try (Connection connection = pool.getConnection()) {
            PreparedStatement stmExist = connection.
                    prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, courseId);
            if (!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course Not Found");
            }
            PreparedStatement stm = connection.prepareStatement("DELETE FROM course WHERE id = ?");
            stm.setInt(1,courseId);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(value = "/{courseId}", produces = "application/json")
    public CourseTO getCourseDetails(@PathVariable int courseId){
        CourseTO course = new CourseTO();
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stm = connection.
                    prepareStatement("SELECT * FROM course WHERE id = ?");
            stm.setInt(1, courseId);
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                int id = rst.getInt("courseId");
                String name = rst.getString("name");
                int duration_in_months = rst.getInt("duration_in_months");
                course.setCourseId(courseId);
                course.setName(name);
                course.setDuration_in_months(duration_in_months);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return course;
    }

    @GetMapping( produces = "application/json")
    public List<CourseTO> getAllCourses(){
        try (Connection connection = pool.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM course ORDER BY id");
            LinkedList<CourseTO> courseList = new LinkedList<>();
            while (rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                int duration_in_months = rst.getInt("duration_in_months");
                courseList.add(new CourseTO(id, name, duration_in_months));
            }
            return courseList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
