package ds.dms.library.dto.book;

import ds.dms.library.entities.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @AllArgsConstructor @ToString
public class RequestBook {
    private String title;
    private String isbn;
    private BookGenre genre;
    private Date publishedDate;
    private Long author_id;
}
