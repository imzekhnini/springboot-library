package ds.dms.library.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor
public class RequestAuthor {
    private String name;
    private String nationality;
    private Date dob;
}
