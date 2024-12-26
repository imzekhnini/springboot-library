package ds.dms.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;
    @Temporal(TemporalType.DATE)
    private Date dob;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;
}
