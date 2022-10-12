package com.manuelsava.demo.university;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manuelsava.demo.book.Book;
import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.enrollment.Enrollment;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity(name = "University")
@Table(name = "university")
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    @NotNull
    @NotBlank
    private String name;

    @Column(
            name="signature",
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR(6)"
    )
    @NotNull
    @NotBlank
    private String signature;

    @Column(name = "address")
    private String address;

    @OneToMany(
            mappedBy = "university",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(
            mappedBy = "university",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @OneToMany(
            mappedBy = "university",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set <Book> books = new HashSet<>();

    public University(String name, String address, String signature) {
        this.name = name;
        this.address = address;
        this.signature = signature;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSignature() {
        return signature;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addEnrollment(Enrollment enrollment){
        if(!enrollments.contains(enrollment)){
            enrollments.add(enrollment);
        }
    }

    public void addCourse(Course course){
        if(!courses.contains(course)){
            courses.add(course);
        }
    }

    public void removeCourse(Course course){
        if(courses.contains(course)){
            courses.remove(course);
        }
    }

    public void removeEnrollment(Enrollment enrollment){
        if(enrollments.contains(enrollment)){
            enrollments.remove(enrollment);
        }
    }

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        University that = (University) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(signature, that.signature) && Objects.equals(address, that.address) && Objects.equals(enrollments, that.enrollments) && Objects.equals(courses, that.courses) && Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, signature, address, enrollments, courses, books);
    }
}
