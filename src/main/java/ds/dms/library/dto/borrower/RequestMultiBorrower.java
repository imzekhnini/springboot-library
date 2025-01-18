package ds.dms.library.dto.borrower;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class RequestMultiBorrower {
    @NotNull(message = "Student Id must not be null.")
    public Long studentId;
    @NotEmpty(message = "Array of books ids must not be null.")
    public List<Long> bookIds;
}
