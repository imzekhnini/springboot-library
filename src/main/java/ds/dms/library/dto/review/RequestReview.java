package ds.dms.library.dto.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RequestReview {
    @NotBlank
    private String reviewDesc;
    @NotNull
    private Long bookId;
    @NotNull
    private Long studentId;
}
