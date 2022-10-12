package com.manuelsava.demo.student;

import com.manuelsava.demo.student.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollStudentRequest {
    private StudentDTO student;
    private Long universityId;
}
