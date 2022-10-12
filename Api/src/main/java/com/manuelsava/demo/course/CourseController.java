package com.manuelsava.demo.course;

import com.manuelsava.demo.course.dto.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("create")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "CREATED")
    public void addCourse(@Valid @RequestBody AddCourseRequest addCourseRequest){
        Course course = modelMapper.map(addCourseRequest.getCourse(), Course.class);
        courseService.addCourse(course, addCourseRequest.getUniversityId());
    }

    @PutMapping("setactive")
    @ResponseStatus(value = HttpStatus.OK, reason = "OK")
    public void setActive(@RequestParam("courseId") Long courseId){
        courseService.setActive(courseId);
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseDTO> getCourse(@RequestParam("courseId") Long courseId){
        Course course = courseService.getCourse(courseId);
        return ResponseEntity.ok().body(modelMapper.map(course, CourseDTO.class));
    }

    @GetMapping("all")
    public List<CourseDTO> getCourse(){
        List<Course> courses = courseService.getCourses();
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("getactive")
    public List<CourseDTO> getActiveCourses(@RequestParam Long universityId) {
        List<Course> courses = courseService.findActiveCoursesByUniversity(universityId);
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("findbyuni")
    public List<CourseDTO> getByUniversity(@RequestParam Long universityId){
        List<Course> courses = courseService.findByUniversity(universityId);
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @PutMapping("update")
    public void updateCourse(@RequestParam("courseId") Long courseId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "professor", required = false) String professor,
                             @RequestParam(value = "cfu", required = false) Integer cfu,
                             @RequestParam(value = "year", required = false) Integer year){
        courseService.update(courseId, name, description, professor, cfu, year);
    }

    @DeleteMapping("delete/{courseId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteCourse(@RequestParam("courseId") Long courseId){
        courseService.deleteCourse(courseId);
    }
}
