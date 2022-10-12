package com.manuelsava.demo.book;

import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UniversityRepository universityRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UniversityRepository universityRepository) {
        this.bookRepository = bookRepository;
        this.universityRepository = universityRepository;
    }

    public void createBook(Book book, Long universityId) {
        Optional<University> university = universityRepository.findById(universityId);
        //check if University exists
        if(university.isEmpty())
            throw new IllegalStateException("University does not exist!");
        if(book.getCopies() <= 0)
            throw new IllegalStateException("Invalid number of copies!");
        //save book
        book.setUniversity(university.get());

        bookRepository.save(book);
    }

    public void deleteBook(Long bookId){
        bookRepository.deleteById(bookId);
    }

    public List<Book> findByUniversity(Long universityId) {
        return bookRepository.findByUniversity(universityId);
    }

    public void update(Long bookId, String title, String description, String author, Integer copies, Integer pages) {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if(optBook.isEmpty())
            throw new IllegalStateException("Book does not exist!");

        Book book = optBook.get();
        if(title != null)
            book.setTitle(title);
        if(description != null)
            book.setDescription(description);
        if(author != null)
            book.setAuthor(author);
        if(copies != null)
            book.setCopies(copies);
        if(pages != null)
            book.setPages(pages);

        bookRepository.save(book);
    }

    public List<Book> findByStudent(Long studentId) {
        return bookRepository.findByStudent(studentId);
    }

    public List<Book> findByTitleLike(String title) {
        return bookRepository.findByTitleLike(title);
    }

    public Book findById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isEmpty())
            throw new IllegalStateException("Book does not exist!");
        return book.get();
    }
}
