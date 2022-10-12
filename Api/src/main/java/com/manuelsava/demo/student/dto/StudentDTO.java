package com.manuelsava.demo.student.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Integer age;
    @JsonIgnore
    private Set<String> enrollments;
    @JsonIgnore
    private Set<String> borrows;
    @JsonIgnore
    private Set<String> courseEnrollments;
}
