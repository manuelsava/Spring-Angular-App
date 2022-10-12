package com.manuelsava.demo.course_enrollment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "CourseEnrollment")
@Table(name = "course_enrollment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enrolled_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime enrolledAt;

    @Column(name = "verbalized_at", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime verbalizedAt;

    @Column(name = "mark", columnDefinition = "INT")
    private Integer mark;

    public CourseEnrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrolledAt = LocalDateTime.now();
    }
}
