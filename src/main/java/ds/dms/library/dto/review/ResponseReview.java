package ds.dms.library.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ResponseReview {
    private String reviewDesc;
    private String bookTitle;
    private String studentName;
}
