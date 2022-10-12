package com.manuelsava.demo.course_enrollment;

import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.course.CourseRepository;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseEnrollmentIntegrationTest {
    private CourseEnrollmentController underTest;

    @Autowired
    private CourseEnrollmentService courseEnrollmentService;

    @Autowired
    private CourseEnrollmentRepository courseEnrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        underTest = new CourseEnrollmentController(courseEnrollmentService);
    }

    @Test
    void itShouldSaveEnrollment() throws Exception {
        //Already saved in Config
        Long studentId = 3L;
        Long courseId = 4L;

        ResultActions send = mockMvc.perform(post("/api/v1/course-enrollment/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()));

        send.andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteEnrollment() throws Exception {
        //Already saved in Config
        Long studentId = 3L;
        Long courseId = 4L;

        ResultActions send = mockMvc.perform(post("/api/v1/course-enrollment/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()));

        send.andExpect(status().isOk());

        Optional<CourseEnrollment> query = courseEnrollmentRepository.findById(5L);
        assertThat(query).isPresent();

        ResultActions delete = mockMvc.perform(delete("/api/v1/course-enrollment/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .param("courseEnrollmentId", query.get().getId().toString()));

        delete.andExpect(status().isOk());
    }

    @Test
    void itShouldVerbalizeMark() throws Exception {
        //Already saved in Config
        Long studentId = 3L;
        Long courseId = 4L;

        ResultActions send = mockMvc.perform(post("/api/v1/course-enrollment/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .param("studentId", studentId.toString())
                .param("courseId", courseId.toString()));

        send.andExpect(status().isOk());

        Optional<CourseEnrollment> query = courseEnrollmentRepository.findById(5L);
        assertThat(query).isPresent();

        ResultActions verbalize = mockMvc.perform(put("/api/v1/course-enrollment/verbalize")
                .contentType(MediaType.APPLICATION_JSON)
                .param("courseEnrollmentId", query.get().getId().toString())
                .param("mark", "24"));

        verbalize.andExpect(status().isOk());
    }
}