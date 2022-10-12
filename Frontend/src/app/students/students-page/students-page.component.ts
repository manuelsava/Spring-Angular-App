import { Component, OnInit } from '@angular/core';
import { StudentsService } from 'src/app/services/students.service';

@Component({
  selector: 'app-students-page',
  templateUrl: './students-page.component.html',
  styleUrls: ['./students-page.component.css']
})
export class StudentsPageComponent implements OnInit {
  loadedItem = 'students';

  constructor(private studentsService: StudentsService) { }

  ngOnInit(): void {
  }

}
