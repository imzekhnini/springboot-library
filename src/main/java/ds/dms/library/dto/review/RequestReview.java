package ds.dms.library.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RequestReview {
    private String reviewDesc;
    private Long bookId;
    private Long studentId;
}
