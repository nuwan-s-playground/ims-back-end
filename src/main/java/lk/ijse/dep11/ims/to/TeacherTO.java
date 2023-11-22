package lk.ijse.dep11.ims.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTO implements Serializable {

    @Null(message = "Id should be null",groups = Create.class)
    @NotNull(message = "Id should not be null",groups = Update.class)
    private Integer id;

    @Pattern(regexp = "^[A-Za-z ]+$",message = "Invalid name")
//    @NotBlank(message = "Name can not be blank.")
    private String name;

    @Pattern(regexp = "^\\d{3}-\\d{7}$",message = "Invalid contact")
//    @NotBlank(message = "Name can not be blank.")
    private String contact;

    public interface Create extends Default{}
    public interface Update extends Default{}
}
