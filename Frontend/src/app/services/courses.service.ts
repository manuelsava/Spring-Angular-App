import { HttpClient } from "@angular/common/http";
import { EventEmitter, Injectable, Output } from "@angular/core";
import { environment } from "src/environments/environment";
import { Course } from "../course/course.model";

@Injectable()
export class CoursesService {
    courses: Course[] = [];
    selectedCourse: Course = {} as Course;
    @Output() courseWasSelected = new EventEmitter<Course>();
    @Output() coursesLoaded = new EventEmitter<void>();

    constructor(public http: HttpClient) {
        this.loadCourses();
    }

    loadCourses() {
        this.http.get(environment.apiUrl + "/api/v1/course/all").subscribe((res: any) => {
            this.courses = [];
            res.forEach((e: any) => {
                console.log(e);
                const c = new Course(e.name, e.description, e.cfu, e.professor, e.year, e.active, e.id);
                this.courses.push(c);
            });

            this.coursesLoaded.emit();
        })
    }

    getCourses(): Course[] {
        return this.courses;
    }

    selectCourse(course: Course) {
        this.selectedCourse = course;
        this.courseWasSelected.emit(this.selectedCourse);
    }
}