import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Student } from '../student.model';
import { StudentsService } from 'src/app/services/students.service';
import { ActivatedRoute, Data, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html',
  styleUrls: ['./student-detail.component.css']
})
export class StudentDetailComponent implements OnInit {
  student: Student = {} as Student;
  closeResult = '';

  constructor(private studentsService: StudentsService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.data.subscribe(
      (data: Data) => {
        this.student = data['student']
      }
    );

    this.studentsService.studentDeleted.subscribe(
      (executed) => {
        if(executed) {
          Swal.fire(
            'Deleted!',
            'Your file has been deleted.',
            'success'
          )
        } else {
          Swal.fire(
            'Error!',
            'Could not delete student!',
            'error'
          )
        }
      }
    )
  }

  async onDelete(studentId: number) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You will permanently delete this student!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then( async (result) => {
      if (result.isConfirmed) {
        this.studentsService.deleteStudent(studentId);
        this.router.navigate(['../'], {relativeTo: this.route});
      }
    })
  }
}
