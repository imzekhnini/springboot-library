package ds.dms.library.mapper.book;

import ds.dms.library.dto.book.RequestBook;
import ds.dms.library.dto.book.ResponseBook;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(source = "author_id", target = "author.id")
    Book toEntity(RequestBook requestBook);

    @Mapping(source = "author.name" , target = "authorName")
    ResponseBook toResponseBook(Book book);
}
