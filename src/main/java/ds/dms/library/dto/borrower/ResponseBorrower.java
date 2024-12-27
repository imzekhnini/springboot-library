package ds.dms.library.dto.borrower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class ResponseBorrower {
    private Long id;
    private Date borrowedDate;
    private Date returnDate;
    private String bookTitle;
    private String studentName;
}
