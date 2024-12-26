package ds.dms.library.controller;

import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.services.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/books")
public class BookController {
    public final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseBook>> getAllBooks(){
        List<ResponseBook> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseBook> addBook(@RequestBody RequestBook requestBook){
        ResponseBook book = bookService.addBook(requestBook);
        return ResponseEntity.ok(book);
    }
}
