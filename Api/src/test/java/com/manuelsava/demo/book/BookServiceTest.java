package com.manuelsava.demo.book;

import com.manuelsava.demo.book.dto.BookDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class BookServiceTest {
    private BookService underTest;

    @Autowired
    private final BookRepository bookRepository = mock(BookRepository.class);

    @Autowired
    private final UniversityRepository universityRepository = mock(UniversityRepository.class);

    @Autowired
    private ModelMapper modelMapper;

    @Captor
    private final ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookRepository, universityRepository);
    }

    @Test
    void itShouldSaveBook() {
        Book book = new Book(
                "Mastering Bitcoin",
                "Understanding Blockchain",
                "Manuel",
                242,
                5
        );

        Long uniId = 1L;
        given(universityRepository.findById(uniId)).willReturn(Optional.of(new University()));

        underTest.createBook(book, uniId);

        then(bookRepository).should().save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(book);
    }

    @Test
    void itShouldNotSaveBookIfUniversityDoesNotExist(){
        Book book = new Book(
                "Mastering Bitcoin",
                "Understanding Blockchain",
                "Manuel",
                242,
                5
        );

        Long uniId = 1L;

        assertThatThrownBy(() -> underTest.createBook(book, uniId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("University does not exist!");
    }

    @Test
    void itShouldDeleteBook() {
        Book book = new Book(
                "Mastering Bitcoin",
                "Understanding Blockchain",
                "Manuel",
                242,
                5
        );

        Long id = 1L;
        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), id);

        underTest.deleteBook(id);

        then(bookRepository).should().deleteById(id);
    }

    @Test
    void itShouldUpdateBook() {
        given(bookRepository.findById(1L)).willReturn(Optional.of(new Book()));
        underTest.update(1L, null, "Desc", "auth", 3, 2);

        then(bookRepository).should().save(captor.capture());
    }

    @Test
    void itShouldNotUpdateBook() {
        given(bookRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.update(1L, null, "Desc", "auth", 3, 2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Book does not exist!");
    }

    @Test
    void itShouldFindById(){
        given(bookRepository.findById(1L)).willReturn(Optional.of(new Book()));
        assertThat(bookRepository.findById(1L)).isPresent();
    }

    @Test
    void itShouldNotFindById(){
        given(bookRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findById(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Book does not exist!");
    }

    @Test
    void itShouldFindByTitle(){
        given(bookRepository.findByTitleLike("Hello")).willReturn(List.of(new Book()));
        assertThat(underTest.findByTitleLike("Hello").size()).isEqualTo(1);
    }
}