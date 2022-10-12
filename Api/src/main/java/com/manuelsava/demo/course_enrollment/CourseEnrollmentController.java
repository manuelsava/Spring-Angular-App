package com.manuelsava.demo.course_enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/course-enrollment/")
public class CourseEnrollmentController {
    private final CourseEnrollmentService courseEnrollmentService;

    @Autowired
    public CourseEnrollmentController(CourseEnrollmentService courseEnrollmentService) {
        this.courseEnrollmentService = courseEnrollmentService;
    }

    @PostMapping("enroll")
    public void enroll(@RequestParam Long studentId, @RequestParam Long courseId){
        courseEnrollmentService.enroll(studentId, courseId);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam Long courseEnrollmentId){
        courseEnrollmentService.delete(courseEnrollmentId);
    }

    @PutMapping("verbalize")
    public void verbalize(@RequestParam Long courseEnrollmentId,
                          @RequestParam Integer mark){
        courseEnrollmentService.verbalize(courseEnrollmentId, mark);
    }
}
