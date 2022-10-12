package com.manuelsava.demo.course_enrollment;

import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.course.CourseRepository;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class CourseEnrollmentServiceTest {
    private CourseEnrollmentService underTest;
    private CourseEnrollmentRepository courseEnrollmentRepository = mock(CourseEnrollmentRepository.class);
    private CourseRepository courseRepository = mock(CourseRepository.class);
    private StudentRepository studentRepository = mock(StudentRepository.class);

    @Captor
    ArgumentCaptor<CourseEnrollment> captor = ArgumentCaptor.forClass(CourseEnrollment.class);

    @BeforeEach
    void setUp() {
        underTest = new CourseEnrollmentService(
                courseEnrollmentRepository,
                courseRepository,
                studentRepository);
    }

    @Test
    void itShouldSaveCourseEnrollment() {
        Long studentId = 1L;
        Long courseId = 2L;

        given(studentRepository.findById(studentId)).willReturn(Optional.of(new Student()));
        given(courseRepository.findById(courseId)).willReturn(Optional.of(new Course()));

        underTest.enroll(studentId, courseId);
        then(courseEnrollmentRepository).should().save(captor.capture());
    }

    @Test
    void itShouldNotSaveCourseEnrollmentWhenStudentIsEmpty() {
        Long studentId = 1L;
        Long courseId = 2L;

        given(studentRepository.findById(studentId)).willReturn(Optional.empty());
        given(courseRepository.findById(courseId)).willReturn(Optional.of(new Course()));

        assertThatThrownBy(() -> underTest.enroll(studentId, courseId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Student does not exist!");
    }

    @Test
    void itShouldNotSaveCourseEnrollmentWhenCoursetIsEmpty() {
        Long studentId = 1L;
        Long courseId = 2L;

        given(studentRepository.findById(studentId)).willReturn(Optional.of(new Student()));
        given(courseRepository.findById(courseId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.enroll(studentId, courseId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Course does not exist!");
    }

    @Test
    void itShouldVerbalizeMark() {
        Long enrollmentId = 1L;

        given(courseEnrollmentRepository.findById(enrollmentId)).willReturn(Optional.of(new CourseEnrollment()));

        underTest.verbalize(enrollmentId, 24);
        then(courseEnrollmentRepository).should().save(captor.capture());
    }

    @Test
    void itShouldNotaVerbalizeMarkWhenMarkIsInvalid() {
        Long enrollmentId = 1L;

        given(courseEnrollmentRepository.findById(enrollmentId)).willReturn(Optional.of(new CourseEnrollment()));

        assertThatThrownBy(() -> underTest.verbalize(enrollmentId, 32))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid mark!");
    }

    @Test
    void itShouldNotaVerbalizeMarkWhenEnrollmentNotExist() {
        Long enrollmentId = 1L;

        given(courseEnrollmentRepository.findById(enrollmentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.verbalize(enrollmentId, 0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Course enrollment not exists!");
    }

    @Test
    void itShouldDeleteEnrollment() {
        Long enrollmentId = 1L;
        underTest.delete(enrollmentId);
        then(courseEnrollmentRepository).should().deleteById(enrollmentId);
    }

}