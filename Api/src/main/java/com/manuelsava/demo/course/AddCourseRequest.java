package com.manuelsava.demo.course;

import com.manuelsava.demo.course.dto.CourseDTO;
import lombok.Data;

@Data
public class AddCourseRequest {
    private final CourseDTO course;
    private final Long universityId;

    public AddCourseRequest(CourseDTO course, Long universityId) {
        this.course = course;
        this.universityId = universityId;
    }
}
