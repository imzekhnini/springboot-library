package ds.dms.library.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data @AllArgsConstructor @ToString
public class ResponseStudent {
    private Long id;
    private String name;
    private String email;
    private Integer age;
}
