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
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Borrowor> borrowers;
}
