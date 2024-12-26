package ds.dms.library.mapper.author;

import ds.dms.library.dto.author.RequestAuthor;
import ds.dms.library.dto.author.ResponseAuthor;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Book;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(RequestAuthor requestAuthor);

    @Mapping(target = "books", source = "books", qualifiedByName = "mapBookIds")
    ResponseAuthor toResponseAuthor(Author author);

    @Named("mapBookIds")
    default List<ResponseAuthor.BookInfo> mapBookIds(List<Book> books){
        if(books == null){
            return Collections.emptyList();
        }
        return books.stream()
                .map(book -> new ResponseAuthor.BookInfo(book.getId(), book.getTitle()))
                .collect(Collectors.toList());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(RequestAuthor requestAuthor, @MappingTarget Author author);
}
