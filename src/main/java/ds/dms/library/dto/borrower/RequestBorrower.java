package ds.dms.library.dto.borrower;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class RequestBorrower {
    @NotNull(message = "Borrowed Date must not be null.")
    private Date borrowedDate;
    private Date returnDate;
    @NotNull(message = "Student id must not be null.")
    private Long studentId;
    @NotNull(message = "Book id must not be null.")
    private Long bookId;
}
