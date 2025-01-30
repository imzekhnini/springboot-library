package ds.dms.library.services.author;

import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.dto.paginate.PaginatedResponse;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {
  List<ResponseAuthor> getAllAuthor();

  ResponseAuthor getAuthorById(Long id);

  ResponseAuthor addAuthor(RequestAuthor requestAuthor);

  ResponseAuthor editAuthor(Long id, RequestAuthor requestAuthor);

  String deleteAuthor(Long id);

  ResponseAuthor getTopAuthorWithMostBooks();

  PaginatedResponse getAuthorsPaginated(Integer page, Integer size, String sortField, String sortDirection);

}
