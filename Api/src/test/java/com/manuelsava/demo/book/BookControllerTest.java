package com.manuelsava.demo.book;

import com.manuelsava.demo.Helper;
import com.manuelsava.demo.book.dto.BookDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    private BookController underTest;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final UniversityRepository universityRepository = mock(UniversityRepository.class);

    private final Helper helper = new Helper();

    @BeforeEach
    void setUp() {
        underTest = new BookController(bookService);
    }

    @Test
    void itShouldCreateBook() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());
    }

    @Test
    void itShouldDeleteBook() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions delete = mockMvc.perform(delete("/api/v1/book/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "5"));

        delete.andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateBook() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions update = mockMvc.perform(put("/api/v1/book/update")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "5")
                .param("title", "New title"));

        update.andExpect(status().isOk());
    }

    @Test
    void itShouldFindByUni() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions findByUni = mockMvc.perform(post("/api/v1/book/findbyuni")
                .contentType(MediaType.APPLICATION_JSON)
                .param("universityId", "1"));

        assertThat(findByUni.andReturn().getResponse().getContentAsString().length()).isGreaterThan(0);
    }

    @Test
    void itShouldNotFindByStudent() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions findByStudent = mockMvc.perform(post("/api/v1/book/findbystudent")
                .contentType(MediaType.APPLICATION_JSON)
                .param("studentId", "3"));

        //Empty List
        assertThat(findByStudent.andReturn().getResponse().getContentAsString().length()).isEqualTo(2);
    }

    @Test
    void itShouldFindByTitleLike() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions findByTitle = mockMvc.perform(post("/api/v1/book/findbytitlelike")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "Test Book"));

        //Empty List
        assertThat(findByTitle.andReturn().getResponse().getContentAsString().length()).isGreaterThan(2);
    }

    @Test
    void itShouldFindById() throws Exception {
        Book book = new Book("Test Book", "Descrizione", "Autore", 100, 10);

        BookRequest bookRequest = new BookRequest(modelMapper.map(book, BookDTO.class), 1L);
        ResultActions create = mockMvc.perform(post("/api/v1/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(bookRequest)));

        create.andExpect(status().isCreated());

        ResultActions findByTitle = mockMvc.perform(get("/api/v1/book/5"));

        //Empty List
        assertThat(findByTitle.andReturn().getResponse().getContentAsString().length()).isGreaterThan(2);
    }
}
