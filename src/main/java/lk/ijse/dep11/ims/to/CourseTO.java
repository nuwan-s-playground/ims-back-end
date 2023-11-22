package lk.ijse.dep11.ims.to;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTO implements Serializable {
    private int courseId;
    private String name;
    private int duration_in_months;
}
