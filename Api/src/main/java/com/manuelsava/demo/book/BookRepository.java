package com.manuelsava.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book as b WHERE b.title = ?1")
    Optional<Book> findByName(String name);

    @Query("SELECT b FROM Book as b WHERE b.university.id = ?1")
    List<Book> findByUniversity(Long universityId);

    @Query("SELECT b FROM Book as b " +
            "JOIN Borrow as bo ON bo.book.id = b.id " +
            "WHERE bo.student.id = ?1")
    List<Book> findByStudent(Long studentId);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    List<Book> findByTitleLike(@Param("title") String title);
}
