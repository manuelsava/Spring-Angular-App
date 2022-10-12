import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { StudentsService } from 'src/app/services/students.service';
import { Student } from '../../student.model';

@Component({
  selector: 'app-student-item',
  templateUrl: './student-item.component.html',
  styleUrls: ['./student-item.component.css']
})
export class StudentItemComponent implements OnInit {
  @Input() student!: Student; 

  constructor(private studentsService: StudentsService) {
  }

  ngOnInit(): void {
  }

  onSelected(){
    this.studentsService.selectStudent(this.student);
  }
}
