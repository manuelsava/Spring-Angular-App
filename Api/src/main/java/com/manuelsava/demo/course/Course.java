package com.manuelsava.demo.course;

import com.manuelsava.demo.course_enrollment.CourseEnrollment;
import com.manuelsava.demo.university.University;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "Course")
@Table(name = "course")
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column (name = "name", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    @NotNull
    private String name;

    @Column (name = "description", nullable = false, columnDefinition = "TEXT")
    @NotNull
    private String description;

    @Column(name = "cfu", nullable = false, columnDefinition = "int")
    @NotNull
    private Integer cfu;

    @Column (name = "professor", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    @NotNull
    private String professor;

    @Column (name = "year", nullable = false, columnDefinition = "int")
    @NotNull
    private Integer year;

    @Column(name = "active", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean active;

    @ManyToOne
    @JoinColumn(
            name = "university_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "course_university_fk"
            )
    )
    private University university;

    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.PERSIST, CascadeType.ALL},
            fetch = FetchType.LAZY
    )
    private Set<CourseEnrollment> courseEnrollments;

    public Course(String name, String description, Integer cfu, String professor, Integer year) {
        this.name = name;
        this.description = description;
        this.cfu = cfu;
        this.professor = professor;
        this.year = year;
        this.active = true;
    }

    public Course(String name, String description, Integer cfu, String professor, Integer year, Boolean active, University university) {
        this.name = name;
        this.description = description;
        this.cfu = cfu;
        this.professor = professor;
        this.year = year;
        this.active = active;
        this.university = university;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCfu() {
        return cfu;
    }

    public String getDescription() {
        return description;
    }

    public String getProfessor() {
        return professor;
    }

    public Integer getYear() {
        return year;
    }

    public University getUniversity() {
        return university;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cfu=" + cfu +
                ", professor='" + professor + '\'' +
                ", year=" + year +
                ", active=" + active +
                ", university=" + university +
                '}';
    }
}
