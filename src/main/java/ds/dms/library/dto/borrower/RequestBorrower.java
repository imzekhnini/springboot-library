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
    @NotNull
    private Date borrowedDate;
    private Date returnDate;
    @NotNull
    private Long studentId;
    @NotNull
    private Long bookId;
}
