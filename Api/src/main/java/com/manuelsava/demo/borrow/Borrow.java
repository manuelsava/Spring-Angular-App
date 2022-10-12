package com.manuelsava.demo.borrow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manuelsava.demo.book.Book;
import com.manuelsava.demo.student.Student;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Borrow")
@Table(name = "borrow")
@NoArgsConstructor
public class Borrow {
    @EmbeddedId
    private BorrowId borrowId;

    @ManyToOne
    @MapsId("studentId")
    @JsonIgnore
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(
                    name = "borrow_student_id_fk"
            )
    )
    private Student student;

    @ManyToOne
    @MapsId("bookId")
    @JsonIgnore
    @JoinColumn(
            name = "book_id",
            foreignKey = @ForeignKey(
                    name = "borrow_book_id_fk"
            )
    )
    private Book book;

    @Column(name = "borrowed_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime borrowedAt;

    public Borrow(BorrowId borrowId, Student student, Book book) {
        this.borrowId = borrowId;
        this.student = student;
        this.book = book;
        borrowedAt = LocalDateTime.now();
    }

    public BorrowId getBorrowId() {
        return borrowId;
    }

    public Student getStudent() {
        return student;
    }

    public Book getBook() {
        return book;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowId(BorrowId borrowId) {
        this.borrowId = borrowId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId=" + borrowId +
                ", student=" + student +
                ", book=" + book +
                ", borrowedAt=" + borrowedAt +
                '}';
    }
}
