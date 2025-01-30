package ds.dms.library.dto.paginate;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaginatedResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse {
  private Object data;
  private Integer currentPage;
  private long totalItems;
  private Integer totalPages;
}
