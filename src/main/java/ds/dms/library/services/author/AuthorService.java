package ds.dms.library.services.author;

import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthor> getAllAuthor();
    ResponseAuthor getAuthorById(Long id);
    ResponseAuthor addAuthor(RequestAuthor requestAuthor);
    ResponseAuthor editAuthor(Long id, RequestAuthor requestAuthor);

}
