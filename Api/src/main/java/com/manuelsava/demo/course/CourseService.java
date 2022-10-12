package com.manuelsava.demo.course;

import com.manuelsava.demo.course_enrollment.CourseEnrollment;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UniversityRepository universityRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         UniversityRepository universityRepository) {
        this.courseRepository = courseRepository;
        this.universityRepository = universityRepository;
    }

    public void addCourse(Course course, Long universityId) {
        Optional<University> university = universityRepository.findById(universityId);

        //check if university exists
        if(university.isEmpty())
            throw new IllegalStateException("University does not exist!");

        //add course
        course.setUniversity(university.get());
        courseRepository.save(course);
    }

    public void setActive(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()){
            Course update = course.get();
            update.setActive(!update.getActive());
            courseRepository.save(update);
        }
        else
            throw new IllegalStateException("Course does not exist!");
    }

    public Course getCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent())
            return course.get();
        else
            throw new IllegalStateException("Course does not exist!");
    }

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isEmpty())
            throw new IllegalStateException("Course does not exist!");
        courseRepository.deleteById(courseId);
    }

    public List<Course> findByUniversity(Long universityId) {
        return courseRepository.findByUniversity(universityId);
    }

    public void update(Long courseId, String name, String description, String professor, Integer cfu, Integer year) {
        Optional<Course> query = courseRepository.findById(courseId);
        if(query.isEmpty())
            throw new IllegalStateException("Course does not exist!");

        Course course = query.get();
        if(name != null)
            course.setName(name);
        if(description != null)
            course.setDescription(description);
        if(professor != null)
            course.setProfessor(professor);
        if(cfu != null){
            if(cfu > 12 || cfu < 3)
                throw new IllegalStateException("Invalid Cfu!");
            course.setCfu(cfu);
        }
        if(year != null){
            if(year > 6 || year < 1)
                throw new IllegalStateException("Invalid year!");
            course.setYear(year);
        }

        courseRepository.save(course);
    }

    public List<Course> findActiveCoursesByUniversity(Long universityId) {
        return courseRepository.findActiveCourses(universityId);
    }
}
