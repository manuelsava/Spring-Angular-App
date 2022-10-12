package com.manuelsava.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student as s WHERE s.email = ?1")
    Optional<Student> findByEmail(String mail);

    @Override
    @Transactional
    @Modifying
    @Query("DELETE FROM Student as s WHERE s.id = ?1")
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Enrollment as e WHERE e.enrollmentId.studentId = ?1")
    void deleteEnrollment(Long studentId);
}
