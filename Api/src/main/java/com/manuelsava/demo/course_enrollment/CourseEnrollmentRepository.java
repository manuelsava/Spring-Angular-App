package com.manuelsava.demo.course_enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    @Query("DELETE FROM CourseEnrollment as c WHERE c.student = ?1")
    void deleteByStudentId(Long studentId);
}
