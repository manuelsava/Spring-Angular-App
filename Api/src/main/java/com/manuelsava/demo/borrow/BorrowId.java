package com.manuelsava.demo.borrow;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BorrowId implements Serializable {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "student_id")
    private Long studentId;

    public Long getBookId() {
        return bookId;
    }

    public Long getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "BorrowId{" +
                "bookId=" + bookId +
                ", studentId=" + studentId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowId borrowId = (BorrowId) o;
        return bookId.equals(borrowId.bookId) && studentId.equals(borrowId.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, studentId);
    }
}
