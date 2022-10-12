package com.manuelsava.demo.course;

import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class CourseServiceTest {

    private CourseService underTest;
    private final CourseRepository courseRepository = mock(CourseRepository.class);
    private final UniversityRepository universityRepository = mock(UniversityRepository.class);

    @Captor
    ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);

    @BeforeEach
    void setUp() {
        underTest = new CourseService(courseRepository, universityRepository);
    }

    @Test
    void itShouldSaveNewCourse() {
        Course course = new Course("Databases", "", 6, "John Doe", 2);
        University university = new University("UNI", "Via Celoria", "UNIMI");

        given(universityRepository.findById(1L)).willReturn(Optional.of(university));

        underTest.addCourse(course, 1L);
        then(courseRepository).should().save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(course);
    }

    @Test
    void itShouldNotSaveCourseIfUniDoesNotExist() {
        Course course = new Course("Databases", "", 6, "John Doe", 2);

        given(universityRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(()-> {
            underTest.addCourse(course, 1L);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("University does not exist!");
    }

    @Test
    void itShouldNotUpdateActive() {
        Long id = 1L;
        given(courseRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(()-> {
            underTest.setActive(id);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Course does not exist!");
    }

    @Test
    void itShouldDeleteCourse(){
        Long id = 1L;
        Course course = new Course("Databases", "", 6, "John Doe", 2);

        given(courseRepository.findById(id)).willReturn(Optional.of(course));

        underTest.deleteCourse(id);
        then(courseRepository).should().deleteById(id);
    }

    @Test
    void itShouldNotDeleteNonExistentCourse(){
        Long id = 1L;

        given(courseRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> {underTest.deleteCourse(id);})
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Course does not exist!");
    }

    @Test
    void itShouldUpdateCourse(){
        Long id = 1L;
        Course course = new Course("Databases", "", 6, "John Doe", 2);
        given(courseRepository.findById(id)).willReturn(Optional.of(course));

        underTest.update(1L,
                "DB",
                "Corso sul management di database",
                "Dohn Joe",
                6,
                3
        );

        then(courseRepository).should().save(captor.capture());
    }
}