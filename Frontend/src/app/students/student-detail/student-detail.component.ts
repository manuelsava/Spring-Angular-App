import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Student } from '../student.model';
import { StudentsService } from 'src/app/services/students.service';

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  student: Student = {} as Student;

  constructor(private studentsService: StudentsService) { 
    this.studentsService.studentWasSelected.subscribe((student) => {
      this.student = student;
    })
  }

  ngOnInit(): void {
  }

  onDelete(studentId: number) {
    this.studentsService.deleteStudent(studentId);
  }
}
