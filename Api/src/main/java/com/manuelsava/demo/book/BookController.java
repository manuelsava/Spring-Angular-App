package com.manuelsava.demo.book;

import com.manuelsava.demo.book.dto.BookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(path = "create")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void createBook(@Valid @RequestBody BookRequest bookRequest){
        Book book = modelMapper.map(bookRequest.getBook(), Book.class);
        Long universityId = bookRequest.getUniversityId();
        bookService.createBook(book, universityId);
    }

    @DeleteMapping(path = "delete")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteBook(@RequestParam("bookId") Long bookId){
        bookService.deleteBook(bookId);
    }

    @PostMapping("findbyuni")
    public List<BookDTO> findByUniversity(@RequestParam Long universityId){
        List<Book> books = bookService.findByUniversity(universityId);
        return books.stream().map(
                book -> modelMapper.map(book, BookDTO.class)
        ).collect(Collectors.toList());
    }

    @PostMapping("findbystudent")
    public List<BookDTO> findByStudent(@RequestParam Long studentId){
        List<Book> books = bookService.findByStudent(studentId);
        return books.stream().map(
                book -> modelMapper.map(book, BookDTO.class)
        ).collect(Collectors.toList());
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookDTO> findById(@PathVariable(name = "bookId") Long bookId){
        Book book = bookService.findById(bookId);
        return ResponseEntity.ok().body(modelMapper.map(book, BookDTO.class));
    }


    @PostMapping("findbytitlelike")
    public List<BookDTO> findByTitle(@RequestParam String title){
        List<Book> books = bookService.findByTitleLike(title);
        return books.stream().map(
                book -> modelMapper.map(book, BookDTO.class)
        ).collect(Collectors.toList());
    }

    @PutMapping("update")
    public void updateBook(@RequestParam("bookId") Long bookId,
                             @RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "author", required = false) String author,
                             @RequestParam(value = "copies", required = false) Integer copies,
                             @RequestParam(value = "pages", required = false) Integer pages){
        bookService.update(bookId, title, description, author, copies, pages);
    }
}
