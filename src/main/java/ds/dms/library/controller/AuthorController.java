package ds.dms.library.controller;

import ds.dms.library.dao.AuthorRepository;
import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.services.author.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {
    public final AuthorService authorService;

    @GetMapping("/")
    public ResponseEntity<List<ResponseAuthor>> getAllAuthors() {
        List<ResponseAuthor> authors = authorService.getAllAuthor(); // Fetch authors from the service
        return ResponseEntity.ok(authors); // Return the list with HTTP 200 status
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAuthor> getAuthorById(@PathVariable Long id){
        ResponseAuthor author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseAuthor> addAuthor(@RequestBody RequestAuthor requestAuthor){
        ResponseAuthor author = authorService.addAuthor(requestAuthor);
        return ResponseEntity.ok(author);
    }
}
