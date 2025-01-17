package ds.dms.library.services.book;

import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.dto.book.ResponseBookDetails;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.BookGenre;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<ResponseBook> getAllBooks();
    ResponseBook getBookById(Long id);
    ResponseBook addBook(RequestBook requestBook);
    ResponseBook updateBook(Long id, RequestBook requestBook);
    String deleteBook(Long id);

    Page<ResponseBook> getBooksPaginated(int page, int size, String sortField, String sortDirection);

    List<ResponseBook> getBooksByAuthorId(Long id);
    List<ResponseBook> getBooksByBookGenre(BookGenre genre);
    List<ResponseBook> getCurrentlyBorrowedBooks();
    List<ResponseBook> getBooksBorrowedByStudentById(Long id);
    List<ResponseBook> getBooksSearchByTitle(String title);
    Map<String, Integer> getBooksCountByGenre();
    List<Map<String,Object>> getTopReviewedBooks();
    List<ResponseStudent> getBorrowersOfBookById(Long id);
    ResponseBookDetails getBookDetailsByBookId(Long id);

}
