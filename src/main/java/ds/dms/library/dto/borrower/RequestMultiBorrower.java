package ds.dms.library.dto.borrower;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class RequestMultiBorrower {
    public Long studentId;
    public List<Long> bookIds;
}
