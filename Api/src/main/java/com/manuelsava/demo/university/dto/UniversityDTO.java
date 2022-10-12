package com.manuelsava.demo.university.dto;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UniversityDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String signature;
    private String address;
    private Set<String> enrollments;
    private Set<String> courses;
    private Set<String> books;
}
