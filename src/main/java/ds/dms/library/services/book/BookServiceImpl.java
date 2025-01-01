package ds.dms.library.services.book;

import ds.dms.library.dao.AuthorRepository;
import ds.dms.library.dao.BookRepository;
import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Book;
import ds.dms.library.entities.BookGenre;
import ds.dms.library.mapper.book.BookMapper;
import ds.dms.library.services.author.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    public final BookRepository bookRepository;
    public final AuthorRepository authorRepository;
    public final BookMapper bookMapper;

    @Override
    public List<ResponseBook> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<ResponseBook> resBooks = books.stream()
                .map(bookMapper::toResponseBook)
                .collect(Collectors.toList());
        return resBooks;
    }

    @Override
    public ResponseBook getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: "+id));
        return bookMapper.toResponseBook(book);
    }

    @Override
    public ResponseBook addBook(RequestBook requestBook) {
        Author author = authorRepository.findById(requestBook.getAuthor_id()).orElseThrow();
        Book book = bookMapper.toEntity(requestBook);
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseBook(savedBook);
    }

    @Override
    public ResponseBook updateBook(Long id, RequestBook requestBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: "+id));
        bookMapper.updateEntityFromRequest(requestBook, book);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponseBook(updatedBook);
    }

    @Override
    public String deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: "+id));
        bookRepository.deleteById(id);
        return "Book id: "+id+" deleted!";
    }

    @Override
    public List<ResponseBook> getBooksByAuthorId(Long id) {
        List<Book> books = bookRepository.findByAuthorId(id);
        List<ResponseBook> resBooks = books.stream()
                .map(bookMapper::toResponseBook).collect(Collectors.toList());
        return resBooks;
    }

    @Override
    public List<ResponseBook> getBooksByBookGenre(BookGenre genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        List<ResponseBook> resBooks = books.stream()
                .map(bookMapper::toResponseBook).collect(Collectors.toList());
        return resBooks;
    }

    @Override
    public List<ResponseBook> getCurrentlyBorrowedBooks() {
        List<Book> books = bookRepository.findCurrentlyBorrowedBooks();
        List<ResponseBook> resBooks = books.stream()
                .map(bookMapper::toResponseBook).collect(Collectors.toList());
        return resBooks;
    }

    @Override
    public List<ResponseBook> getBooksBorrowedByStudentById(Long id) {
        List<Book> books = bookRepository.findBooksBorrowedByStudentById(id);
        List<ResponseBook> resBooks = books.stream()
                .map(bookMapper::toResponseBook).collect(Collectors.toList());
        return resBooks;
    }
}
