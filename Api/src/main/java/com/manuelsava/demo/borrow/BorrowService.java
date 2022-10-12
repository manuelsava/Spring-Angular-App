package com.manuelsava.demo.borrow;

import com.manuelsava.demo.book.Book;
import com.manuelsava.demo.book.BookRepository;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository,
                         StudentRepository studentRepository,
                         BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;

    }

    public void createBorrow (BorrowRequest borrowRequest){
        Optional<Student> student = studentRepository.findById(borrowRequest.getStudentId());
        Optional<Book> book = bookRepository.findById(borrowRequest.getBookId());
        if(student.isEmpty())
            throw new IllegalStateException("Student does not exist!");
        if(book.isEmpty())
            throw new IllegalStateException("Book does not exist!");

        Borrow borrow = new Borrow(
                new BorrowId(borrowRequest.getBookId(), borrowRequest.getStudentId()),
                student.get(),
                book.get()
        );

        borrowRepository.save(borrow);
    }

    public void removeBorrow(BorrowRequest borrowRequest){
        Optional<Student> student = studentRepository.findById(borrowRequest.getStudentId());
        Optional<Book> book = bookRepository.findById(borrowRequest.getBookId());
        if(student.isEmpty())
            throw new IllegalStateException("Student does not exist!");
        if(book.isEmpty())
            throw new IllegalStateException("Book does not exist!");

        BorrowId borrowId = new BorrowId(borrowRequest.getBookId(), borrowRequest.getStudentId());
        Optional<Borrow> query = borrowRepository.findById(borrowId);

        if(query.isEmpty())
            throw new IllegalStateException("Borrow does not exist!");

        Borrow borrow = query.get();
        borrow.getStudent().removeBorrow(borrow);
        borrow.getBook().removeBorrow(borrow);

        borrowRepository.save(borrow);
    }

    public Optional<Book> getByName(String name){
        return bookRepository.findByName(name);
    }
}
