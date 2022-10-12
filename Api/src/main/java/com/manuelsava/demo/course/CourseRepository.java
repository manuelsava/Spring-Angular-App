package com.manuelsava.demo.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course as c WHERE c.university.id = ?1")
    List<Course> findByUniversity(Long university_id);

    @Query("SELECT c FROM Course as c WHERE c.university.id = ?1 AND c.active = true")
    List<Course> findActiveCourses(Long university_id);
}
