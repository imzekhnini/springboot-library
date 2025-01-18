package ds.dms.library.dto.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data @AllArgsConstructor @ToString
public class RequestStudent {
    @NotBlank
    @Size(min = 6, message = "Name must be at least 6 characters long.")
    private String name;
    @NotBlank
    @Email(message = "Email should be valid.")
    private String email;
    @NotNull
    @Min(value = 8, message = "Age must be at least 8.")
    private Integer age;
}
