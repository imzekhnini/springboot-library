package ds.dms.library.services.author;

import ds.dms.library.dao.AuthorRepository;
import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.entities.Author;
import ds.dms.library.mapper.author.AuthorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    public final AuthorRepository authorRepository;
    public final AuthorMapper authorMapper;

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
        Author author = authorRepository.findById(id).orElseThrow();
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
        Author author = authorRepository.findById(id).orElseThrow();
        authorMapper.updateEntityFromRequest(requestAuthor,author);
        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.toResponseAuthor(updatedAuthor);
    }

    @Override
    public String deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: "+ id));
        authorRepository.delete(author);
        return "Author " + id + " deleted!";
    }
}
