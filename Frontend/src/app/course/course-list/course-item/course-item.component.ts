import { Component, Input, OnInit } from '@angular/core';
import { CoursesService } from 'src/app/services/courses.service';
import { Course } from '../../course.model';

@Component({
  selector: 'app-course-item',
  templateUrl: './course-item.component.html',
  styleUrls: ['./course-item.component.css']
})
export class CourseItemComponent implements OnInit {
  @Input() course!: Course;

  constructor(private courseService: CoursesService){}

  ngOnInit(): void {
  }

}
