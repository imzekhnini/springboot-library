package ds.dms.library.services.author;

import ds.dms.library.dao.AuthorRepository;
import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.dto.paginate.PaginatedResponse;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Book;
import ds.dms.library.mapper.author.AuthorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
  public final AuthorRepository authorRepository;
  public final AuthorMapper authorMapper;

  public static final Integer MAX_PAGE_SIZE = 10;

  @Override
  public List<ResponseAuthor> getAllAuthor() {
    List<Author> authors = authorRepository.findAll();
    List<ResponseAuthor> authorsRes = authors.stream()
        .map(authorMapper::toResponseAuthor)
        .collect(Collectors.toList());
    return authorsRes;
  }

  @Override
  public ResponseAuthor getAuthorById(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    ResponseAuthor authorRes = authorMapper.toResponseAuthor(author);
    return authorRes;
  }

  @Override
  public ResponseAuthor addAuthor(RequestAuthor requestAuthor) {
    Author author = authorMapper.toEntity(requestAuthor);
    Author createdAuthor = authorRepository.save(author);
    return authorMapper.toResponseAuthor(createdAuthor);
  }

  @Override
  public ResponseAuthor editAuthor(Long id, RequestAuthor requestAuthor) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    authorMapper.updateEntityFromRequest(requestAuthor, author);
    Author updatedAuthor = authorRepository.save(author);
    return authorMapper.toResponseAuthor(updatedAuthor);
  }

  @Override
  public String deleteAuthor(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    authorRepository.delete(author);
    return "Author id: " + id + " deleted!";
  }

  @Override
  public ResponseAuthor getTopAuthorWithMostBooks() {
    Author author = authorRepository.findTopAuthorWithMostBooks();
    return authorMapper.toResponseAuthor(author);
  }

  @Override
  public PaginatedResponse getAuthorsPaginated(Integer page, Integer size, String sortField, String sortDirection) {
    size = size > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : size;
    Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Author> authors = authorRepository.findAll(pageable);
    PaginatedResponse response = new PaginatedResponse();
    response.setData(authors.map(authorMapper::toResponseAuthor).getContent());
    response.setCurrentPage(authors.getNumber());
    response.setTotalPages(authors.getTotalPages());
    response.setTotalItems(authors.getTotalElements());
    return response;
  }
}
