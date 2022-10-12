package com.manuelsava.demo.book.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private String author;
    private Integer avaliableCopies;
    private Integer pages;
    @NotNull
    private Integer copies;
    private Set<String> university;
    private Set<String> borrows;
    private Boolean isBorrowed;
}
