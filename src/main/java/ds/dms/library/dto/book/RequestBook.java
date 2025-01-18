package ds.dms.library.dto.book;

import ds.dms.library.entities.BookGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor @ToString
public class RequestBook {
    @NotBlank
    @Size(min = 6, message = "Title must not be null.")
    private String title;
    @NotBlank
    @Size(min = 6, message = "ISBN must not be null.")
    private String isbn;
    @NotNull(message = "Genre must not be null.")
    private BookGenre genre;
    @NotNull(message = "Published date must not be null.")
    private Date publishedDate;
    @NotNull(message = "Author id must not be null.")
    private Long author_id;
}
