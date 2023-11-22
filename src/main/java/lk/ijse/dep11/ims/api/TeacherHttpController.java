package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.TeacherTO;
import org.springframework.http.HttpStatus;
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


    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/{teacherId}",produces = "application/json")
    public void getTeacherDetails(@PathVariable int teacherId){


    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<TeacherTO> getAllTeachersDetails(){

        return new ArrayList<>();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TeacherTO createTeacher(@RequestBody TeacherTO teacher){

        return teacher;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value="/{teacherId}",consumes = "application/json")
    public TeacherTO updateTeacher( @RequestBody TeacherTO teacher ){

        System.out.println("patch");
        return teacher;
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{teacherId}")
    public void deleteTeacher(@PathVariable int teacherId){

        System.out.println("delete");
    }
}
