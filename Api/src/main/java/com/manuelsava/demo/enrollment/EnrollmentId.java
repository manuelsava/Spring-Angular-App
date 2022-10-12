package com.manuelsava.demo.enrollment;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentId implements Serializable {
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "university_id")
    private Long universityId;

    public Long getStudentId() {
        return studentId;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return studentId.equals(that.studentId) && universityId.equals(that.universityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, universityId);
    }
}
