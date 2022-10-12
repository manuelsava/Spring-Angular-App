package com.manuelsava.demo.course_enrollment;

import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.course.CourseRepository;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CourseEnrollmentService {
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseEnrollmentService(CourseEnrollmentRepository courseEnrollmentRepository,
                                   CourseRepository courseRepository,
                                   StudentRepository studentRepository) {
        this.courseEnrollmentRepository = courseEnrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public void enroll(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);

        if(student.isEmpty())
            throw new IllegalStateException("Student does not exist!");
        if(course.isEmpty())
            throw new IllegalStateException("Course does not exist!");

        CourseEnrollment courseEnrollment = new CourseEnrollment(student.get(), course.get());
        courseEnrollmentRepository.save(courseEnrollment);
    }

    public void verbalize(Long enrollmentId, Integer mark){
        if(mark < 0 || mark > 31)
            throw new IllegalStateException("Invalid mark!");
        Optional<CourseEnrollment> optional = courseEnrollmentRepository.findById(enrollmentId);
        if(optional.isEmpty())
            throw new IllegalStateException("Course enrollment not exists!");

        optional.get().setMark(mark);
        optional.get().setVerbalizedAt(LocalDateTime.now());

        courseEnrollmentRepository.save(optional.get());
    }

    public void delete(Long enrollmentId){
        courseEnrollmentRepository.deleteById(enrollmentId);
    }
}
