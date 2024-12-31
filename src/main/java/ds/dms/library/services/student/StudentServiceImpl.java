package ds.dms.library.services.student;

import ds.dms.library.dao.StudentRepository;
import ds.dms.library.dto.student.RequestStudent;
import ds.dms.library.dto.student.ResponseStudent;
import ds.dms.library.entities.Author;
import ds.dms.library.entities.Student;
import ds.dms.library.mapper.student.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    public final StudentRepository studentRepository;
    public final StudentMapper studentMapper;

    @Override
    public List<ResponseStudent> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        List<ResponseStudent> resStudents = students.stream()
                .map(studentMapper::toResponseStudent)
                .collect(Collectors.toList());
        return resStudents;
    }

    @Override
    public ResponseStudent getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        return studentMapper.toResponseStudent(student);
    }

    @Override
    public ResponseStudent addStudent(RequestStudent requestStudent) {
        Student student = studentMapper.toEntity(requestStudent);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseStudent(savedStudent);
    }

    @Override
    public ResponseStudent updateStudent(Long id, RequestStudent requestStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        studentMapper.updateEntityFromRequest(requestStudent, student);
        Student updatedSaved = studentRepository.save(student);
        return studentMapper.toResponseStudent(updatedSaved);
    }

    @Override
    public String deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: "+id));
        studentRepository.deleteById(id);
        return "Student id: "+ id +" deleted!";
    }
}
