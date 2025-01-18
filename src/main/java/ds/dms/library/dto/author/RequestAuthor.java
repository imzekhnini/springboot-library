package ds.dms.library.dto.author;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor
public class RequestAuthor {
    @NotBlank
    private String name;
    @NotBlank
    @Min(5)
    private String nationality;
    @NotNull
    @Past
    private Date dob;
}
