package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.TeacherTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teachers")
public class TeacherHttpController {
    private HikariDataSource pool;
    public  TeacherHttpController(){
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("9710");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_ims");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize",10);
        pool = new HikariDataSource(config);
        System.out.println("create connection pool");


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/{teacherId}",produces = "application/json")
    public TeacherTO getTeacherDetails(@PathVariable int teacherId){
        TeacherTO teacher = new TeacherTO();
        try(Connection connection = pool.getConnection()){
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM teacher WHERE id=?");
            stm.setInt(1,teacherId);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name =resultSet.getString("name");
                String contact =resultSet.getString("contact");
                teacher.setId(id);
                teacher.setName(name);
                teacher.setContact(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacher;

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<TeacherTO> getAllTeachersDetails(){
        List<TeacherTO> teacherList = new ArrayList<>();
        try(Connection connection = pool.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher ORDER BY id");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name =resultSet.getString("name");
                String contact =resultSet.getString("contact");
                TeacherTO teacher = new TeacherTO();
                teacher.setId(id);
                teacher.setName(name);
                teacher.setContact(contact);
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teacherList;

    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TeacherTO createTeacher(@RequestBody  @Validated(TeacherTO.Create.class) TeacherTO teacher){
        try(Connection connection = pool.getConnection()){
            System.out.println("post");
            PreparedStatement stm = connection.prepareStatement("INSERT INTO  teacher(name,contact) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,teacher.getName());
            stm.setString(2,teacher.getContact());
            int teacherId = stm.executeUpdate();
            System.out.println(teacherId);
            teacher.setId(teacherId);
            return teacher;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value="/{teacherId}",consumes = "application/json")
    public TeacherTO updateTeacher( @RequestBody @Validated(TeacherTO.Update.class) TeacherTO teacher ){
        try(Connection connection = pool.getConnection()){
            PreparedStatement stm = connection.prepareStatement("UPDATE teacher SET name=?,contact=? WHERE id=?");
            stm.setString(1,teacher.getName());
            stm.setString(2,teacher.getContact());
            stm.setInt(3,teacher.getId());
            stm.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("patch");
        return teacher;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{teacherId}")
    public void deleteTeacher(@PathVariable int teacherId){
        try(Connection connection = pool.getConnection()){
            PreparedStatement stm = connection.prepareStatement("DELETE FROM teacher WHERE id=?");
            stm.setInt(1,teacherId);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
