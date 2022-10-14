import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { StudentsService } from '../services/students.service';
import { Student } from './student.model';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {
  selectedStudent!: Student;

  constructor(private studentsService: StudentsService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

}
