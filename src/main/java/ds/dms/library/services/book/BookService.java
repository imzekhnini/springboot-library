package ds.dms.library.services.book;

import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.entities.BookGenre;

import java.util.List;

public interface BookService {
    List<ResponseBook> getAllBooks();
    ResponseBook getBookById(Long id);
    ResponseBook addBook(RequestBook requestBook);
    ResponseBook updateBook(Long id, RequestBook requestBook);
    String deleteBook(Long id);

    List<ResponseBook> getBooksByAuthorId(Long id);
    List<ResponseBook> getBooksByBookGenre(BookGenre genre);
    List<ResponseBook> getCurrentlyBorrowedBooks();
    List<ResponseBook> getBooksBorrowedByStudentById(Long id);
}
