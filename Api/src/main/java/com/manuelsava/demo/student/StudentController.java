package com.manuelsava.demo.student;

import com.manuelsava.demo.student.dto.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping(path = "{studentId}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("studentId") Long studentId){
        Student student = studentService.getStudent(studentId).get();

        //Conversion to DTO
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping(path="all")
    public List<StudentDTO> getStudents(){
        List<Student> students = studentService.getStudents();

        //Map students to DTOs
        return students.stream().map(
                student -> modelMapper.map(student, StudentDTO.class)
        ).collect(Collectors.toList());
    }

    @PostMapping (path = "enroll")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void enrollStudent(@Valid @RequestBody EnrollStudentRequest enrollStudentRequest){
        Student student = modelMapper.map(enrollStudentRequest.getStudent(), Student.class);
        Long universityId = enrollStudentRequest.getUniversityId();
        studentService.enrollStudent(student, universityId);
    }

    @DeleteMapping(path = "delete/{studentId}")
    @ResponseStatus(code = HttpStatus.OK, reason = "DELETED")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
}
