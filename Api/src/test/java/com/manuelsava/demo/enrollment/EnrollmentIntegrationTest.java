package com.manuelsava.demo.enrollment;

import com.manuelsava.demo.Helper;
import com.manuelsava.demo.student.EnrollStudentRequest;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import com.manuelsava.demo.student.dto.StudentDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentIntegrationTest {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private Helper helper = new Helper();

    @Test
    void itShouldEnrollStudent() throws Exception {
        University university = new University(
                "Politecnico di Milano",
                "Via Celoria, 9",
                "POLIMI"
        );

        Student student = new Student(
                "John",
                "Doe",
                LocalDate.of(1998, 4, 14)
        );

        ResultActions uniReq = mockMvc.perform(post("/api/v1/university/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJson(university)));

        uniReq.andExpect(status().isCreated());
        Optional<University> query = universityRepository.findByName("Politecnico di Milano");
        assertThat(query).isPresent();

        Long uniId = query.get().getId();

        EnrollStudentRequest enrollStudentRequest = new EnrollStudentRequest(modelMapper.map(student, StudentDTO.class), uniId);

        ResultActions enrReq = mockMvc.perform(post("/api/v1/student/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(helper.objectToJsonDate(enrollStudentRequest)));

        enrReq.andExpect(status().isCreated());

        assertThat(studentRepository.findByEmail("john.doe@students.polimi.it"))
                .isPresent();
    }
}
