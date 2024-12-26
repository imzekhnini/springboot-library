package ds.dms.library.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor
public class ResponseAuthor {
    private Long id;
    private String name;
    private String nationality;
    private Date dob;
    private List<BookInfo> books;

    @Data
    @AllArgsConstructor
    public static class BookInfo {
        private Long id;
        private String title;
    }
}
