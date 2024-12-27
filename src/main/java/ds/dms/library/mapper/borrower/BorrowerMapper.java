package ds.dms.library.mapper.borrower;

import ds.dms.library.dao.BookRepository;
import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.borrower.RequestBorrower;
import ds.dms.library.dto.borrower.ResponseBorrower;
import ds.dms.library.entities.Borrower;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {StudentRepository.class, BookRepository.class})
public interface BorrowerMapper {

    Borrower toEntity(RequestBorrower requestBorrower);

    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "student.name", target = "studentName")
    ResponseBorrower toResponseBorrower(Borrower borrower);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "book", expression = "java(requestBorrower.getBookId() != null ? bookRepository.findById(requestBorrower.getBookId()).orElse(null) : borrower.getBook())")
    @Mapping(target = "student", expression = "java(requestBorrower.getStudentId() != null ? studentRepository.findById(requestBorrower.getStudentId()).orElse(null) : borrower.getStudent())")
    void updateEntityFromRequest(RequestBorrower requestBorrower, @MappingTarget Borrower borrower, @Context StudentRepository studentRepository, @Context BookRepository bookRepository);

}
