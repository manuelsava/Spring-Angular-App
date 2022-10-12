package com.manuelsava.demo.student;

import com.manuelsava.demo.course_enrollment.CourseEnrollmentRepository;
import com.manuelsava.demo.enrollment.EnrollmentRepository;
import com.manuelsava.demo.student.dto.StudentDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class StudentServiceTest {
    StudentService underTest;
    StudentRepository studentRepository = mock(StudentRepository.class);
    UniversityRepository universityRepository = mock(UniversityRepository.class);
    EnrollmentRepository enrollmentRepository = mock(EnrollmentRepository.class);
    CourseEnrollmentRepository courseEnrollmentRepository = mock(CourseEnrollmentRepository.class);

    @Autowired
    ModelMapper modelMapper;

    @Captor
    ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository, universityRepository, enrollmentRepository);
    }

    @Test
    void itShouldSaveStudent() {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMI"
        );

        given(universityRepository.findById(university.getId())).willReturn(
                Optional.of(university)
        );

        given(studentRepository.findByEmail("manuel.sava@students.unimi.it")).willReturn(
                Optional.empty()
        );

        given(studentRepository.save(student)).willReturn(student);

        underTest.enrollStudent(student, university.getId());
        then(studentRepository).should().save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void itShouldNotSaveStudentWhenUniNotExists() {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMI"
        );

        given(universityRepository.findById(university.getId())).willReturn(
                Optional.empty()
        );

        given(studentRepository.findByEmail("manuel.sava@students.unimi.it")).willReturn(
                Optional.empty()
        );

        assertThatThrownBy(() -> underTest.enrollStudent(student, university.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("University does not exists!");
    }

    @Test
    void itShouldSaveStudentWhenMailExists() {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMI"
        );

        given(universityRepository.findById(university.getId())).willReturn(
                Optional.of(university)
        );

        given(studentRepository.findByEmail("manuel.savà@students.unimi.it")).willReturn(
                Optional.of(student)
        );

        given(studentRepository.save(student)).willReturn(student);

        underTest.enrollStudent(student, university.getId());
        then(studentRepository).should().save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getEmail())
                .isEqualTo("manuel.savà1@students.unimi.it");
    }
}