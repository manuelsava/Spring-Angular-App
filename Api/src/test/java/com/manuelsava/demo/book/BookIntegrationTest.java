package com.manuelsava.demo.book;

import com.manuelsava.demo.Helper;
import com.manuelsava.demo.book.dto.BookDTO;
import com.manuelsava.demo.borrow.Borrow;
import com.manuelsava.demo.borrow.BorrowId;
import com.manuelsava.demo.borrow.BorrowRepository;
import com.manuelsava.demo.borrow.BorrowRequest;
import com.manuelsava.demo.enrollment.Enrollment;
import com.manuelsava.demo.enrollment.EnrollmentId;
import com.manuelsava.demo.enrollment.EnrollmentRepository;
import com.manuelsava.demo.student.EnrollStudentRequest;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import com.manuelsava.demo.student.dto.StudentDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTest {
    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MockMvc mockMvc;

    private final Helper helper = new Helper();

    @Test
    void itShouldCreateBook() throws Exception {
        String title = "My test Book";

        Book book = new Book(
                 title,
                "Desc",
                "Author",
                242,
                5
        );

        University university =new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University saved = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), saved.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        Optional<Book> optBook = bookRepository.findByName(title);

        assertThat(optBook).isPresent();
        assertThat(optBook.get().getUniversity().getId())
                .isEqualTo(saved.getId());
    }

    @Test
    void itShouldFindById() {
        //Given
        //When
        //Then
    }

    @Test
    void itShouldBorrowBook() throws Exception {
        String title = "My test Book";

        Book book = new Book(
                title,
                "Desc",
                "Author",
                242,
                5
        );

        University university = new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University savedUniversity = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), savedUniversity.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        Optional<Book> optBook = bookRepository.findByName(title);

        assertThat(optBook).isPresent();
        assertThat(optBook.get().getUniversity().getId())
                .isEqualTo(savedUniversity.getId());

        //Enrolls student
        Student student = new Student("John", "Doe", LocalDate.of(1998, 4, 14));
        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(modelMapper.map(student, StudentDTO.class), savedUniversity.getId());

        ResultActions enroll = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJsonDate(enrollStudentRequest)));
        enroll.andExpect(status().isCreated());

        //Borrow
        BorrowRequest borrowRequest = new BorrowRequest(7L, 6L);
        ResultActions borrow = mockMvc.perform(post("/api/v1/borrows/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(borrowRequest)));

        borrow.andExpect(status().isOk());
        assertThat(bookRepository.findByName(title).get().getAvaliableCopies()).isEqualTo(4);
        assertThat(bookRepository.findByName(title).get().getBorrows().size()).isEqualTo(1);
        assertThat(bookRepository.findByName(title).get().getBorrowed()).isEqualTo(true);
    }

    @Test
    void itShoulDeleteBorrow() throws Exception {
        String title = "My test Book";

        Book book = new Book(
                title,
                "Desc",
                "Author",
                242,
                5
        );

        University university = new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University savedUniversity = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), savedUniversity.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        Optional<Book> optBook = bookRepository.findByName(title);

        assertThat(optBook).isPresent();
        assertThat(optBook.get().getUniversity().getId())
                .isEqualTo(savedUniversity.getId());

        //Enrolls student
        Student student = new Student("John", "Doe", LocalDate.of(1998, 4, 14));
        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(modelMapper.map(student, StudentDTO.class), savedUniversity.getId());

        ResultActions enroll = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJsonDate(enrollStudentRequest)));
        enroll.andExpect(status().isCreated());

        //Borrow
        BorrowRequest borrowRequest = new BorrowRequest(7L, 6L);
        ResultActions borrow = mockMvc.perform(post("/api/v1/borrows/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(borrowRequest)));

        borrow.andExpect(status().isOk());
        assertThat(bookRepository.findByName(title).get().getAvaliableCopies()).isEqualTo(4);
        assertThat(bookRepository.findByName(title).get().getBorrows().size()).isEqualTo(1);
        assertThat(bookRepository.findByName(title).get().getBorrowed()).isEqualTo(true);

        ResultActions remove = mockMvc.perform(delete("/api/v1/borrows/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(borrowRequest)));

        remove.andExpect(status().isOk());
        assertThat(bookRepository.findByName(title).get().getAvaliableCopies()).isEqualTo(5);
        assertThat(bookRepository.findByName(title).get().getBorrows().size()).isEqualTo(0);
        assertThat(bookRepository.findByName(title).get().getBorrowed()).isEqualTo(false);
    }

    @Test
    void itShouldUpdateBook() throws Exception {
        String title = "My test Book";

        Book book = new Book(
                title,
                "Desc",
                "Author",
                242,
                5
        );

        University university = new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University savedUniversity = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), savedUniversity.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        ResultActions update = mockMvc.perform(put("/api/v1/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "6")
                .param("author", "John Doe")
                .param("copies", "10"));

        update.andExpect(status().isOk());

        Book updated = bookRepository.findById(6L).get();

        assertThat(updated.getAuthor()).isEqualTo("John Doe");
        assertThat(updated.getCopies()).isEqualTo(10);
        assertThat(updated.getDescription()).isEqualTo("Desc");
    }

    @Test
    @Transactional
    void itShouldDeleteBook() throws Exception {
        String title = "My test Book 123";

        Book book = new Book(
                title,
                "Desc",
                "Author",
                242,
                5
        );

        University university = new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University savedUniversity = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), savedUniversity.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        ResultActions delete = mockMvc.perform(delete("/api/v1/book/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "6"));

        delete.andExpect(status().isOk());

        assertThat(bookRepository.findByName(title).isPresent()).isEqualTo(false);
    }

    @Test
    public void itShouldFindByStudent() throws Exception {
        String title = "My test Book";

        Book book = new Book(
                title,
                "Desc",
                "Author",
                242,
                5
        );

        University university = new University("Politecnico di Milano", "Via Celoria","POLIMI");
        University savedUniversity = universityRepository.save(university);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), savedUniversity.getId());

        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        Optional<Book> optBook = bookRepository.findByName(title);

        assertThat(optBook).isPresent();
        assertThat(optBook.get().getUniversity().getId())
                .isEqualTo(savedUniversity.getId());

        //Enrolls student
        Student student = new Student("John", "Doe", LocalDate.of(1998, 4, 14));
        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(modelMapper.map(student, StudentDTO.class), savedUniversity.getId());

        ResultActions enroll = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJsonDate(enrollStudentRequest)));
        enroll.andExpect(status().isCreated());

        //Borrow
        BorrowRequest borrowRequest = new BorrowRequest(7L, 6L);
        ResultActions borrow = mockMvc.perform(post("/api/v1/borrows/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(borrowRequest)));

        borrow.andExpect(status().isOk());

        //find By Borrow
        List<Book> books = bookService.findByStudent(7L);
        assertThat(books.size()).isEqualTo(1);
    }
}
