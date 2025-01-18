package ds.dms.library.dto.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RequestReview {
    @NotBlank
    @Size(min = 10, message = "Description must be at least 10 characters long.")
    private String reviewDesc;
    @NotNull(message = "Book id must not be null.")
    private Long bookId;
    @NotNull(message = "Student id must not be null.")
    private Long studentId;
}
