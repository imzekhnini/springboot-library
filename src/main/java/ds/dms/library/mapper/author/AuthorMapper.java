package ds.dms.library.mapper.author;

import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(RequestAuthor requestAuthor);
    ResponseAuthor toResponseAuthor(Author author);
}
