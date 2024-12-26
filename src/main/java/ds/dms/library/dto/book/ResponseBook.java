package ds.dms.library.dto.book;

import ds.dms.library.entities.BookGenre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class ResponseBook {
    private Long id;
    private String title;
    private String isbn;
    private BookGenre genre;
    private Date publishedDate;
    private String authorName;
}
