package ds.dms.library.seeder;

import com.github.javafaker.Faker;
import ds.dms.library.dao.*;
import ds.dms.library.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "app.seed-data", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    public final Faker faker = new Faker();
    public final Random random = new Random();
    public final AuthorRepository authorRepository;
    public final StudentRepository studentRepository;
    public final BookRepository bookRepository;
    public final ReviewRepository reviewRepository;
    public final BorrowerRepository borrowerRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10 ; i++){
            Author author = new Author();
            author.setName(faker.book().author());
            author.setNationality(faker.nation().nationality());
            author.setDob(faker.date().birthday(60,80));
            authorRepository.save(author);
        }
        for (int i = 0; i < 10 ; i++){
            Student student = new Student();
            student.setName(faker.leagueOfLegends().champion());
            student.setEmail(faker.name().username()+"@example.com");
            student.setAge(faker.number().numberBetween(12,60));
            studentRepository.save(student);
        }
        for (int i = 0; i < 50 ; i++){
            Book book = new Book();
            book.setTitle(faker.book().title());
            book.setIsbn(faker.code().isbn10());
            book.setPublishedDate(faker.date().birthday());
            book.setGenre(getRandomGenre());
            Author author = authorRepository.findById(Long.valueOf(faker.number().numberBetween(1,10))).orElse(null);
            book.setAuthor(author);
            bookRepository.save(book);
        }
        for (int i = 0; i < 50 ; i++){
            Review review = new Review();
            Book book = bookRepository.findById(Long.valueOf(faker.number().numberBetween(1,50))).orElse(null);
            Student student = studentRepository.findById(Long.valueOf(faker.number().numberBetween(1,10))).orElse(null);
            review.setReviewDesc(faker.lorem().sentence(6));
            review.setBook(book);
            review.setStudent(student);
            reviewRepository.save(review);
        }
        for (int i = 0; i < 40 ; i++){
            Borrower borrower = new Borrower();
            Book book = bookRepository.findById(Long.valueOf(faker.number().numberBetween(1,50))).orElse(null);
            Student student = studentRepository.findById(Long.valueOf(faker.number().numberBetween(1,10))).orElse(null);
            Date borrowedDate = faker.date().past(700, TimeUnit.DAYS);
            borrower.setBorrowedDate(borrowedDate);
            borrower.setBook(book);
            borrower.setStudent(student);

            if(faker.random().nextInt(1,100) <= 70){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(borrowedDate);
                int daysToAdd = faker.number().numberBetween(7,60);
                calendar.add(Calendar.DAY_OF_YEAR,daysToAdd);
                borrower.setReturnDate(calendar.getTime());
            }else{
                borrower.setReturnDate(null);
            }
            borrowerRepository.save(borrower);
        }
        System.out.println("Fake data seeded!");

    }
    public BookGenre getRandomGenre(){
        BookGenre[] genres = BookGenre.values();
        return genres[random.nextInt(genres.length)];
    }

}
