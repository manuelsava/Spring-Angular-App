import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../student.model';
import { environment } from 'src/environments/environment';
import { StudentsService } from 'src/app/services/students.service';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  students: Student[] = [];

  ngOnInit(): void {}

  constructor(public http: HttpClient, private studentService: StudentsService) {
    this.students = studentService.getStudents();
    this.studentService.studentsLoaded.subscribe(() => {
      this.students = studentService.students;
    })
  }

  onStudentSelected(student: Student){
    this.studentService.selectStudent(student);
  }
}
