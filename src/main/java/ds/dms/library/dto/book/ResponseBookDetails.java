package ds.dms.library.dto.book;

import ds.dms.library.entities.BookGenre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseBookDetails {
    private Long id;
    private String title;
    private String isbn;
    private BookGenre genre;
    private Date publishedDate;
    private AuthorInfo author;
    private List<BorrowersInfo> borrowers;
    private List<ReviewsInfo> reviews;

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class AuthorInfo {
        private String name;
        private String nationality;
        private Date dob;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class BorrowersInfo {
        private String studentName;
        private Date borrowedDate;
        private Date returnDate;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class ReviewsInfo {
        private String studentName;
        private String reviewDesc;
    }
}
