package com.manuelsava.demo.course.dto;

import com.manuelsava.demo.course_enrollment.CourseEnrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer cfu;
    @NotBlank
    @NotNull
    private String professor;
    @NotBlank
    @NotNull
    private Integer year;
    private Boolean active;
    private String university;
    private Set<String> courseEnrollments;
}
