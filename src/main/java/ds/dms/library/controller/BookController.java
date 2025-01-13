package ds.dms.library.controller;

import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.dto.review.ResponseReview;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.BookGenre;
import ds.dms.library.services.book.BookService;
import ds.dms.library.services.review.ReviewService;
import ds.dms.library.services.student.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/books")
public class BookController {
    public final BookService bookService;
    public final StudentService studentService;
    public final ReviewService reviewService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseBook>> getAllBooks(){
        List<ResponseBook> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<ResponseBook>> getBooksByAuthorId(@PathVariable Long id){
        List<ResponseBook> books = bookService.getBooksByAuthorId(id);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseBook>> getBooksSearchByTitle(@RequestParam("title") String title){
        List<ResponseBook> books = bookService.getBooksSearchByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/countByGenre")
    public ResponseEntity<Map<String, Integer>> getCountBooksByGenre(){
        Map<String, Integer> response = bookService.getBooksCountByGenre();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<ResponseBook>> getBooksByGenre(@PathVariable BookGenre genre){
        List<ResponseBook> books = bookService.getBooksByBookGenre(genre);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/borrowedCurrently")
    public ResponseEntity<List<ResponseBook>> getCurrentlyBorrowedBooks(){
        List<ResponseBook> books = bookService.getCurrentlyBorrowedBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/student/{id}/borrowed")
    public ResponseEntity<List<ResponseBook>> getBooksBorrowedByStudentById(@PathVariable Long id){
        List<ResponseBook> books = bookService.getBooksBorrowedByStudentById(id);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBook> getBookById(@PathVariable Long id){
        ResponseBook book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}/borrowers")
    public ResponseEntity<List<ResponseStudent>> getBorrowersByBookId(@PathVariable Long id){
        List<ResponseStudent> students = studentService.getBorrowersByBookId(id);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ResponseReview>> getReviewsByBookId(@PathVariable Long id){
        List<ResponseReview> reviews = reviewService.getReviewByBookId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBook> addBook(@RequestBody RequestBook requestBook){
        ResponseBook book = bookService.addBook(requestBook);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ResponseBook> updateBook(@PathVariable Long id, @RequestBody RequestBook requestBook){
        ResponseBook book = bookService.updateBook(id, requestBook);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String,String>> deleteBook(@PathVariable Long id){
        String message = bookService.deleteBook(id);
        Map<String,String> response = new HashMap<>();
        response.put("message",message);
        return ResponseEntity.ok(response);
    }

}
