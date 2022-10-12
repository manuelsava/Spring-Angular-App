package com.manuelsava.demo.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.manuelsava.demo.borrow.Borrow;
import com.manuelsava.demo.course_enrollment.CourseEnrollment;
import com.manuelsava.demo.enrollment.Enrollment;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity(name = "Student")
@Table(name = "student")
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
    private String email;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    //Relationships
    @OneToMany(
            mappedBy = "student",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(
            mappedBy = "student",
            //cascade = {CascadeType.PERSIST},
            fetch = FetchType.LAZY
    )
    private Set<Borrow> borrows = new HashSet<>();

    @OneToMany(
            mappedBy = "student",
            //cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<CourseEnrollment> courseEnrollments;

    public Student(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public Set<CourseEnrollment> getCourseEnrollments() {
        return courseEnrollments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public void setCourseEnrollments(Set<CourseEnrollment> courseEnrollments) {
        this.courseEnrollments = courseEnrollments;
    }

    public void addEnrollment(Enrollment enrollment){
        if(!enrollments.contains(enrollment)){
            enrollments.add(enrollment);
        }
    }

    public void removeEnrollment(Enrollment enrollment){
        if(enrollments.contains(enrollment)){
            enrollments.remove(enrollment);
        }
    }

    public void removeEnrollments() {
        for(Enrollment enrollment : new HashSet<>(enrollments)) {
            removeEnrollment(enrollment);
        }
    }

    public void addBorrow(Borrow borrow){
        if(!borrows.contains(borrow)){
            borrows.add(borrow);
        }
    }

    public void removeBorrow(Borrow borrow){
        if(borrows.contains(borrow)){
            borrows.remove(borrow);
        }
    }

    public void addCourseEnrollment(CourseEnrollment enrollment){
        if(!courseEnrollments.contains(enrollment)){
            courseEnrollments.add(enrollment);
        }
    }

    public void removeCourseEnrollment(CourseEnrollment enrollment){
        if(courseEnrollments.contains(enrollment)){
            courseEnrollments.remove(enrollment);
        }
    }

    public void removeCourseEnrollments() {
        for(CourseEnrollment course : new HashSet<>(courseEnrollments)) {
            removeCourseEnrollment(course);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
