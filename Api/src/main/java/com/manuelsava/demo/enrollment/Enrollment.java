package com.manuelsava.demo.enrollment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.university.University;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Enrollment")
@Table(name = "enrollment")
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @EmbeddedId
    private EnrollmentId enrollmentId;

    @ManyToOne()
    @MapsId("studentId")
    @JsonIgnore
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_student_id_fk"
            )
    )
    private Student student;

    @ManyToOne()
    @MapsId("universityId")
    @JsonIgnore
    @JoinColumn(
            name = "university_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_university_id_fk"
            )
    )
    private University university;

    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime createdAt;

    public Enrollment(EnrollmentId enrollmentId, Student student, University university){
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.university = university;
        this.createdAt = LocalDateTime.now();
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public University getUniversity() {
        return university;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", student=" + student +
                ", university=" + university +
                ", createdAt=" + createdAt +
                '}';
    }
}
