package com.manuelsava.demo.borrow;

import com.manuelsava.demo.book.BookRepository;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import com.manuelsava.demo.book.Book;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BorrowServiceTest {
    private BorrowService underTest;

    @Autowired
    BorrowRepository borrowRepository = mock(BorrowRepository.class);

    @Autowired
    StudentRepository studentRepository = mock(StudentRepository.class);

    @Autowired
    BookRepository bookRepository = mock(BookRepository.class);

    @Captor
    ArgumentCaptor<Borrow> captor = ArgumentCaptor.forClass(Borrow.class);

    @BeforeEach
    void setUp() {
        underTest = new BorrowService(borrowRepository, studentRepository, bookRepository);
    }

    @Test
    void itShouldBorrowBookToStudent() {
        Student student = new Student("Manuel", "Savà", LocalDate.of(1998, 4, 14));
        Book book = new Book("Mastering Bitcoin", "Desc", "Auth", 242, 5);

        Long studentId = 1L;
        Long bookId = 1L;

        given(studentRepository.findById(studentId)).willReturn(Optional.of(student));
        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        BorrowRequest borrowRequest = new BorrowRequest(studentId, bookId);
        underTest.createBorrow(borrowRequest);
        then(borrowRepository).should().save(captor.capture());

        assertThat(captor.getValue().getStudent()).isEqualTo(student);
        assertThat(captor.getValue().getBook()).isEqualTo(book);
    }

    @Test
    void itShouldNotBorrowIfStudentDoesNotExist(){
        Student student = new Student("Manuel", "Savà", LocalDate.of(1998, 4, 14));
        Book book = new Book("Mastering Bitcoin", "Desc", "Auth", 242, 5);

        Long studentId = 1L;
        Long bookId = 1L;

        given(studentRepository.findById(studentId)).willReturn(Optional.empty());
        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        BorrowRequest borrowRequest = new BorrowRequest(studentId, bookId);
        assertThatThrownBy(() -> underTest.createBorrow(borrowRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Student does not exist!");
    }

    @Test
    void itShouldNotBorrowIfBookDoesNotExist(){
        Student student = new Student("Manuel", "Savà", LocalDate.of(1998, 4, 14));
        Book book = new Book("Mastering Bitcoin", "Desc", "Auth", 242, 5);

        Long studentId = 1L;
        Long bookId = 1L;

        given(studentRepository.findById(studentId)).willReturn(Optional.of(student));
        given(bookRepository.findById(bookId)).willReturn(Optional.empty());

        BorrowRequest borrowRequest = new BorrowRequest(studentId, bookId);
        assertThatThrownBy(() -> underTest.createBorrow(borrowRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Book does not exist!");
    }

    @Test
    void itShouldRemoveBorrow(){
        Long studentId = 1L;
        Long bookId = 2L;

        BorrowId borrowId = new BorrowId(bookId, studentId);

        given(studentRepository.findById(studentId)).willReturn(Optional.of(new Student()));
        given(bookRepository.findById(bookId)).willReturn(Optional.of(new Book()));
        given(borrowRepository.findById(borrowId)).willReturn(Optional.of(
                new Borrow(borrowId, mock(Student.class), mock(Book.class))));

        underTest.removeBorrow(new BorrowRequest(studentId, bookId));

        then(borrowRepository).should().save(captor.capture());
    }
}