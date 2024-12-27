package ds.dms.library.mapper.student;

import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(RequestStudent requestStudent);

    ResponseStudent toResponseStudent(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(RequestStudent requestStudent,@MappingTarget Student student);
}
