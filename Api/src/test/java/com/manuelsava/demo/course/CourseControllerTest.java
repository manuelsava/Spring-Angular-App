package com.manuelsava.demo.course;

import com.manuelsava.demo.Helper;
import com.manuelsava.demo.course.dto.CourseDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import com.manuelsava.demo.university.UniversityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {
    private CourseController courseController;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Helper helper = new Helper();

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        courseController = new CourseController(courseService);
    }

    @Test
    void itShouldPostCourse() throws Exception {
        Course course = new Course("Databases", "", 6, "John Doe", 2);
        AddCourseRequest request = new AddCourseRequest(modelMapper.map(course, CourseDTO.class), 1L);

        ResultActions res = mockMvc.perform(post("/api/v1/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(request)));

        res.andExpect(status().isCreated());
    }

    @Test
    void itShouldSetActiveCourse() throws Exception {
        Course course = new Course("Databases", "", 6, "John Doe", 2);

        AddCourseRequest request = new AddCourseRequest(modelMapper.map(course, CourseDTO.class), 1L);

        ResultActions res = mockMvc.perform(post("/api/v1/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(request)));

        ResultActions res1 = mockMvc.perform(put("/api/v1/course/setactive")
                .contentType(MediaType.APPLICATION_JSON)
                .param("courseId", "4"));

        res1.andExpect(status().isOk());
        assertThat(courseService.getCourse(4L).getActive()).isEqualTo(false);
    }

    @Test
    void itShouldFindByUniversity() throws Exception {
        Course course = new Course("Databases", "", 6, "John Doe", 2);

        AddCourseRequest request = new AddCourseRequest(modelMapper.map(course, CourseDTO.class), 1L);

        ResultActions res = mockMvc.perform(post("/api/v1/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(request)));

        res.andExpect(status().isCreated());

        ResultActions find = mockMvc.perform(post("/api/v1/course/findbyuni")
                .contentType(MediaType.APPLICATION_JSON)
                .param("universityId", "1"));

        find.andExpect(status().isOk());

        List<Course> courses = courseService.findByUniversity(1L);
        assertThat(courses.size()).isGreaterThan(0);
    }

    @Test
    void itShouldUpdateCourse() throws Exception {
        Course course = new Course("Databases", "", 6, "John Doe", 2);
        University university = new University("UNIA", "Via Celoria", "UNIMII");

        AddCourseRequest request = new AddCourseRequest(modelMapper.map(course, CourseDTO.class), 1L);

        universityService.saveUniversity(university);

        ResultActions res = mockMvc.perform(post("/api/v1/course/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(request)));

        ResultActions update = mockMvc.perform(put("/api/v1/course/update")
                .contentType(MediaType.APPLICATION_JSON)
                .param("courseId", "4")
                .param("name", "DB")
                .param("year", "3")
                .param("professor", "Dohn Joe"));

        update.andExpect(status().isOk());

        Course updated = courseService.getCourse(4L);

        assertThat(updated.getName()).isEqualTo("DB");
        assertThat(updated.getYear()).isEqualTo(3);
        assertThat(updated.getProfessor()).isEqualTo("Dohn Joe");
        assertThat(updated.getCfu()).isEqualTo(6);
    }

    @Test
    void itShouldGetActiveCourses() throws Exception {
        Course course0 = new Course("Databases", "", 6, "John Doe", 2);
        Course course1 = new Course("Computer Science", "", 12, "Dohn Joe", 3);
        University university = new University("UNI", "Via Celoria", "UNIMIT");

        University saved = universityRepository.save(university);
        course0.setUniversity(saved);
        course1.setUniversity(saved);

        courseRepository.saveAll(List.of(course1, course0));
        courseService.setActive(7L);

        ResultActions res = mockMvc.perform(get("/api/v1/course/getactive")
                .contentType(MediaType.APPLICATION_JSON)
                .param("universityId", saved.getId().toString()));

        res.andExpect(status().isOk());
        List<Course> query = courseService.findActiveCoursesByUniversity(saved.getId());
        assertThat(query.size()).isEqualTo(1);
    }
}