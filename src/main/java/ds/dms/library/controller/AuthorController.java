package ds.dms.library.controller;

import ds.dms.library.dao.AuthorRepository;
import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.dto.paginate.PaginatedResponse;
import ds.dms.library.entities.Author;
import ds.dms.library.services.author.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {
  public final AuthorService authorService;

  @GetMapping("/")
  public ResponseEntity<List<ResponseAuthor>> getAllAuthors() {
    List<ResponseAuthor> authors = authorService.getAllAuthor();
    return ResponseEntity.ok(authors);
  }

  @GetMapping("/paginate")
  public ResponseEntity<PaginatedResponse> getAuthorsPaginated(
      @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "id") String sortField, @RequestParam(defaultValue = "asc") String sortDirection) {
    PaginatedResponse response = authorService.getAuthorsPaginated(page, size, sortField, sortDirection);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseAuthor> getAuthorById(@PathVariable Long id) {
    ResponseAuthor author = authorService.getAuthorById(id);
    return ResponseEntity.ok(author);
  }

  @GetMapping("/with-most-books")
  public ResponseEntity<ResponseAuthor> getTopAuthorWithMostBooks() {
    ResponseAuthor author = authorService.getTopAuthorWithMostBooks();
    return ResponseEntity.ok(author);
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseAuthor> addAuthor(@Valid @RequestBody RequestAuthor requestAuthor) {
    ResponseAuthor author = authorService.addAuthor(requestAuthor);
    return ResponseEntity.ok(author);
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<ResponseAuthor> updateAuthor(@PathVariable Long id, @RequestBody RequestAuthor requestAuthor) {
    ResponseAuthor author = authorService.editAuthor(id, requestAuthor);
    return ResponseEntity.ok(author);
  }

  @DeleteMapping("/{id}/delete")
  public ResponseEntity<Map<String, String>> deleteAuthor(@PathVariable Long id) {
    String message = authorService.deleteAuthor(id);
    Map<String, String> response = new HashMap<>();
    response.put("massage", message);
    return ResponseEntity.ok(response);
  }
}
