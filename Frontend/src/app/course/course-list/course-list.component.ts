import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CoursesService } from 'src/app/services/courses.service';
import { Course } from '../course.model';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];

  constructor(private courseService: CoursesService) {
    this.courseService.getCourses();
    this.courseService.coursesLoaded.subscribe(() => {
      this.courses = courseService.courses;
    })
  }

  ngOnInit(): void {
  }

  onCourseSelected(course: Course) {
    this.courseService.selectCourse(course);
  }
}
