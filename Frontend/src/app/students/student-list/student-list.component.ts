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
  @Output() studentWasSelected = new EventEmitter<Student>();
  students: Student[] = [
    //new Student("Manuel", "manuelsava98@gmail.com", new Date("1998-04-14"), 24),
    //new Student("Greta", "arosiogreta@gmail.com", new Date("1999-07-10"), 23),
  ];

  ngOnInit(): void {}

  constructor(public http: HttpClient, private studentService: StudentsService) {
    this.studentService.studentsLoaded.subscribe(() => {
      this.students = studentService.students;
    })
  }

  onStudentSelected(student: Student){
    this.studentService.selectStudent(student);
  }
}
