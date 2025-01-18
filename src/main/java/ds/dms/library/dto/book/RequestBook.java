package ds.dms.library.dto.book;

import ds.dms.library.entities.BookGenre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor @ToString
public class RequestBook {
    @NotBlank
    private String title;
    @NotBlank
    private String isbn;
    @NotNull
    private BookGenre genre;
    @NotNull
    private Date publishedDate;
    @NotNull
    private Long author_id;
}
