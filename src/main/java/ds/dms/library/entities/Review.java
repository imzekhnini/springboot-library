package ds.dms.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewDesc;

    @ManyToMany()
    private List<Book> books;

    @ManyToOne()
    private Student student;
}
