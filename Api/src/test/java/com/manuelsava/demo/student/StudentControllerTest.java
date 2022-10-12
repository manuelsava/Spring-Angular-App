package com.manuelsava.demo.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.manuelsava.demo.student.dto.StudentDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityController;
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
import java.text.DateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {
    private StudentController underTest;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        underTest = new StudentController(studentService);
    }

    @Test
    void itShouldEnrollNewStudent() throws Exception {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMIB"
        );

        universityService.saveUniversity(university);

        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        assertThat(studentDTO.getFirstName()).isEqualTo(student.getFirstName());
        assertThat(studentDTO.getLastName()).isEqualTo(student.getLastName());
        assertThat(studentDTO.getDateOfBirth()).isEqualTo(student.getDateOfBirth());

        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(studentDTO, university.getId());

        ResultActions res = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(enrollStudentRequest))
        );

        res.andExpect(status().isCreated());
    }

    @Test
    void itShouldGetEnrolledStudent() throws Exception {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMIB"
        );

        universityService.saveUniversity(university);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(studentDTO, university.getId());

        ResultActions res = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(enrollStudentRequest))
        );

        res.andExpect(status().isCreated());

        ResultActions get = mockMvc.perform(get("/api/v1/student/3"));
        String content = get.andReturn().getResponse().getContentAsString();
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    void itShouldGetAllEnrolledStudent() throws Exception {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMIB"
        );

        universityService.saveUniversity(university);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(studentDTO, university.getId());

        ResultActions res = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(enrollStudentRequest))
        );

        res.andExpect(status().isCreated());

        ResultActions get = mockMvc.perform(get("/api/v1/student/all"));
        String content = get.andReturn().getResponse().getContentAsString();
        assertThat(content.length()).isGreaterThan(0);
    }

    @Test
    void itShouldDeleteStudent() throws Exception {
        Student student = new Student(
                "Manuel",
                "Savà",
                LocalDate.of(1998, 4, 14)
        );

        University university = new University(
                "Università degli Studi di Milano",
                "Via Celoria",
                "UNIMIB"
        );

        universityService.saveUniversity(university);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);

        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(studentDTO, university.getId());

        ResultActions res = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(enrollStudentRequest))
        );

        res.andExpect(status().isCreated());

        Long studentId = 3L;
        ResultActions del = mockMvc.perform(delete("/api/v1/student/delete/" + studentId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        del.andExpect(status().isOk());

        assertThat(studentRepository.findAll().size()).isEqualTo(1);
    }

    private String objectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}