package lk.ijse.dep11.ims.to;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTO implements Serializable {
    @Null(message = "Id should be empty", groups = Create.class)
    //@NotNull(message = "should not be null", groups = Update.class)
    private Integer courseId;

    @NotBlank(message = "name should not be empty")
    private String name;

    //@Null(message = "Status should be empty", groups = Update.class)
    @NotNull(message = "duration should not be empty")
    private Integer duration_in_months;

    public interface Update extends Default {}
    public interface Create extends Default{}
}
