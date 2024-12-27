package ds.dms.library.dto.borrower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class RequestBorrower {
    private Date borrowedDate;
    private Date returnDate;
    private Long studentId;
    private Long bookId;
}
